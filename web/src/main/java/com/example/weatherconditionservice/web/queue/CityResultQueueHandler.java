package com.example.weatherconditionservice.web.queue;

import com.example.weatherconditionservice.common.dto.CityDto;
import com.example.weatherconditionservice.web.service.ICityResultHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CityResultQueueHandler
{
    private final AmqpTemplate resultProblemQueueTemplate;

    private final ICityResultHandlerService cityResultHandlerService;

    @RabbitListener(queues = "${messaging.queue.result}", containerFactory = "resultQueueListener")
    public void handleMessage(CityDto result)
    {
        try
        {
            cityResultHandlerService.executeMessage(result);
        }
        catch (Exception e)
        {
            log.error("Could not handle result for City: {} and Date: {}", result.getCity(), result.getResult().getDate(), e);

            resultProblemQueueTemplate.convertAndSend(result);
        }
    }
}
