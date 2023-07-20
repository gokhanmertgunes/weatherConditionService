package com.example.weatherconditionservice.worker.queue;

import com.example.weatherconditionservice.common.dto.QueueRequestDto;
import com.example.weatherconditionservice.worker.service.ICityRequestHandlerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CityRequestQueueHandlerTests
{
    @InjectMocks
    private CityRequestQueueHandler cityRequestQueueHandler;

    @Mock
    private ICityRequestHandlerService service;

    @Mock(name = "requestProblemQueueTemplate")
    private AmqpTemplate cityRequestProblemQueueTemplate;

    @Test
    public void handleMessage_success()
    {
        QueueRequestDto dto = new QueueRequestDto();

        cityRequestQueueHandler.handleMessage(dto);

        verify(service).executeMessage(dto);
    }

    @Test
    public void handleMessage_fail()
    {
        QueueRequestDto dto = new QueueRequestDto();

        doThrow(RuntimeException.class).when(service).executeMessage(dto);

        cityRequestQueueHandler.handleMessage(dto);

        verify(cityRequestProblemQueueTemplate).convertAndSend(dto);
    }
}
