package com.example.weatherconditionservice.web.queue;

import com.example.weatherconditionservice.common.dto.QueueCoordResultDto;
import com.example.weatherconditionservice.web.service.ICoordResultHandlerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.AmqpTemplate;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CoordResultQueueHandlerTests
{
    @InjectMocks
    private CoordResultQueueHandler coordResultQueueHandler;

    @Mock
    private ICoordResultHandlerService service;

    @Mock(name = "resultProblemQueueTemplate")
    private AmqpTemplate cityResultProblemQueueTemplate;

    @Test
    public void handleMessage_success()
    {
        QueueCoordResultDto dto = new QueueCoordResultDto();

        coordResultQueueHandler.handleMessage(dto);

        verify(service).executeMessage(dto);
    }

    @Test
    public void handleMessage_fail()
    {
        QueueCoordResultDto dto = new QueueCoordResultDto();

        doThrow(RuntimeException.class).when(service).executeMessage(dto);

        coordResultQueueHandler.handleMessage(dto);

        verify(cityResultProblemQueueTemplate).convertAndSend(dto);
    }
}
