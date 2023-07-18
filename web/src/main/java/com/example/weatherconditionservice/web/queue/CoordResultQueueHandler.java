package com.example.weatherconditionservice.web.queue;

import com.example.weatherconditionservice.common.dto.CityCoordinatesDto;
import com.example.weatherconditionservice.common.dto.CityDto;
import com.example.weatherconditionservice.common.dto.QueueCoordResultDto;
import com.example.weatherconditionservice.web.service.ICityResultHandlerService;
import com.example.weatherconditionservice.web.service.ICoordResultHandlerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CoordResultQueueHandler
{
    private final AmqpTemplate coordResultProblemQueueTemplate;

    private final ICoordResultHandlerService coordResultHandlerService;

    @RabbitListener(queues = "${messaging.coord.queue.result}", containerFactory = "resultQueueListener")
    public void handleMessage(QueueCoordResultDto result)
    {
        try
        {
            coordResultHandlerService.executeMessage(result);
        }
        catch (Exception e)
        {
            log.error("Could not handle result for City: {}", result.getCity(), e);

            coordResultProblemQueueTemplate.convertAndSend(result);
        }
    }
}
