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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CoordRequestHandlerServiceTests
{
    @InjectMocks
    private CoordRequestHandlerService coordRequestHandlerService;

    @Mock
    private ICrawlerClient crawlerClient;

    @Mock
    private AmqpTemplate coordResultQueueTemplate;

    @Captor
    private ArgumentCaptor<QueueCoordResultDto> cityArgumentCaptor;

    @Test
    public void test_ExecuteMessage() {
        QueueCoordRequestDto requestDto = QueueCoordRequestDto.builder().city("Ankara").startDate(LocalDate.of(2023,7,15)).endDate(LocalDate.of(2023,7,16)).build();
        CityCoordinatesDto coordinatesDto = CityCoordinatesDto.builder().lat(39.9199).lon(32.8543).build();
        when(crawlerClient.fetchCoordinateInformation("Ankara")).thenReturn(coordinatesDto);

        QueueCoordResultDto queueCoordResultDto = new QueueCoordResultDto(requestDto.getCity(), requestDto.getStartDate(), requestDto.getEndDate(), coordinatesDto);

        coordRequestHandlerService.executeMessage(requestDto);

        verify(coordResultQueueTemplate).convertAndSend(cityArgumentCaptor.capture());
        assertThat(cityArgumentCaptor.getValue().getCity()).isEqualTo(queueCoordResultDto.getCity());
    }
}
