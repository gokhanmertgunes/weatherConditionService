package com.example.weatherconditionservice.worker.queue;

import com.example.weatherconditionservice.common.dto.QueueCoordRequestDto;
import com.example.weatherconditionservice.worker.service.ICoordRequestHandlerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CoordRequestQueueHandlerTests
{
    @InjectMocks
    private CoordRequestQueueHandler coordRequestQueueHandler;

    @Mock
    private ICoordRequestHandlerService service;

    @Mock(name = "requestProblemQueueTemplate")
    private AmqpTemplate coordRequestProblemQueueTemplate;

    @Test
    public void handleMessage_success()
    {
        QueueCoordRequestDto dto = new QueueCoordRequestDto();

        coordRequestQueueHandler.handleMessage(dto);

        verify(service).executeMessage(dto);
    }

    @Test
    public void handleMessage_fail()
    {
        QueueCoordRequestDto dto = new QueueCoordRequestDto();

        doThrow(RuntimeException.class).when(service).executeMessage(dto);

        coordRequestQueueHandler.handleMessage(dto);

        verify(coordRequestProblemQueueTemplate).convertAndSend(dto);
    }
}
