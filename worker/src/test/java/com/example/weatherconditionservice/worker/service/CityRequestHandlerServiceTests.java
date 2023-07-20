package com.example.weatherconditionservice.worker.service;

import com.example.weatherconditionservice.common.dto.*;
import com.example.weatherconditionservice.worker.client.ICrawlerClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CityRequestHandlerServiceTests
{
    @InjectMocks
    private CityRequestHandlerService requestHandlerService;

    @Mock
    private ICrawlerClient crawlerClient;

    @Mock
    private AmqpTemplate cityResultQueueTemplate;

    @Captor
    private ArgumentCaptor<CityDto> cityArgumentCaptor;

    @Test
    public void test_ExecuteMessage() {
        QueueRequestDto request = QueueRequestDto.builder()
                .city("Ankara")
                .coord(CityCoordinatesDto.builder().lon(32.8543).lat(39.9199).build())
                .date(LocalDate.of(2023, 7, 15))
                .build();

        CityCoordinatesDto coordinatesDto = CityCoordinatesDto.builder().lat(request.getCoord().getLat()).lon(request.getCoord().getLon()).build();
        when(crawlerClient.fetchCoordinateInformation("Ankara")).thenReturn(coordinatesDto);

        CityCategoriesDto categoriesDto = CityCategoriesDto.builder()
                .so2("Good")
                .o3("Good")
                .co("Good")
                .build();
        when(crawlerClient.fetchWeatherInformation(coordinatesDto.getLat(),coordinatesDto.getLon(),LocalDate.of(2023,7,15))).thenReturn(categoriesDto);

        requestHandlerService.executeMessage(request);
        verify(cityResultQueueTemplate).convertAndSend(cityArgumentCaptor.capture());
        assertThat(cityArgumentCaptor.getValue().getCity()).isEqualTo("Ankara");
    }
}

