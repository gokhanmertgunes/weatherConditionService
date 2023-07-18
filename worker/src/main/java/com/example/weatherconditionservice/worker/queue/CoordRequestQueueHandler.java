package com.example.weatherconditionservice.worker.queue;

import com.example.weatherconditionservice.common.dto.QueueCoordRequestDto;
import com.example.weatherconditionservice.worker.service.ICoordRequestHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoordRequestQueueHandler
{
    private final AmqpTemplate coordRequestProblemQueueTemplate;

    private final ICoordRequestHandlerService coordRequestHandlerService;


    @RabbitListener(queues = "${messaging.coord.queue.request}", containerFactory = "requestQueueListener")
    public void handleMessage(QueueCoordRequestDto request)
    {
        try
        {
            coordRequestHandlerService.executeMessage(request);
        }
        catch (Exception e)
        {
            log.error("Could not handle coordinate request for City: {}", request.getCity(), e);

            coordRequestProblemQueueTemplate.convertAndSend(request);
        }
    }
}
