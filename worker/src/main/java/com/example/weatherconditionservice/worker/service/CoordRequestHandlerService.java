package com.example.weatherconditionservice.worker.service;

import com.example.weatherconditionservice.common.dto.*;
import com.example.weatherconditionservice.worker.client.ICrawlerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoordRequestHandlerService implements ICoordRequestHandlerService
{

    private final AmqpTemplate coordResultQueueTemplate;

    private final ICrawlerClient crawlerClient;

    @Override
    public void executeMessage(QueueCoordRequestDto request)
    {
        CityCoordinatesDto coordinatesDto = crawlerClient.fetchCoordinateInformation(request.getCity());

        QueueCoordResultDto queueCoordResultDto = new QueueCoordResultDto(request.getCity(), request.getStartDate(), request.getEndDate(), coordinatesDto);

        coordResultQueueTemplate.convertAndSend(queueCoordResultDto);
    }
}
