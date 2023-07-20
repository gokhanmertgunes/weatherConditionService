package com.example.weatherconditionservice.web.queue;

import com.example.weatherconditionservice.common.dto.CityCategoriesDto;
import com.example.weatherconditionservice.common.dto.CityDto;
import com.example.weatherconditionservice.common.dto.CityResultsDto;
import com.example.weatherconditionservice.web.service.ICityResultHandlerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import java.time.LocalDate;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CityResultQueueHandlerTests
{
    @InjectMocks
    private CityResultQueueHandler cityResultQueueHandler;

    @Mock
    private ICityResultHandlerService service;

    @Mock(name = "resultProblemQueueTemplate")
    private AmqpTemplate cityResultProblemQueueTemplate;

    @Test
    public void handleMessage_success()
    {
        CityDto dto = new CityDto();

        cityResultQueueHandler.handleMessage(dto);

        verify(service).executeMessage(dto);
    }

    @Test
    public void handleMessage_fail()
    {
        CityDto dto = CityDto.builder().city("Ankara").result(CityResultsDto.builder().date(LocalDate.of(2023,1,1)).categories(CityCategoriesDto.builder().co("Good").o3("Good").so2("Good").build()).build()).build();

        doThrow(RuntimeException.class).when(service).executeMessage(dto);

        cityResultQueueHandler.handleMessage(dto);

        verify(cityResultProblemQueueTemplate).convertAndSend(dto);
    }
}
