package com.example.weatherconditionservice.worker.queue;

import com.example.weatherconditionservice.common.dto.QueueCoordRequestDto;
import com.example.weatherconditionservice.common.dto.QueueRequestDto;
import com.example.weatherconditionservice.worker.service.ICityRequestHandlerService;
import com.example.weatherconditionservice.worker.service.ICoordRequestHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CityRequestQueueHandler
{
    private final AmqpTemplate requestProblemQueueTemplate;

    private final ICityRequestHandlerService requestHandlerService;


    @RabbitListener(queues = "${messaging.queue.request}", containerFactory = "requestQueueListener")
    public void handleMessage(QueueRequestDto request)
    {
        try
        {
            requestHandlerService.executeMessage(request);
        }
        catch (Exception e)
        {
            log.error("Could not handle request for City: {} Date: {}", request.getCity(),request.getDate(), e);

            requestProblemQueueTemplate.convertAndSend(request);
        }
    }


}
