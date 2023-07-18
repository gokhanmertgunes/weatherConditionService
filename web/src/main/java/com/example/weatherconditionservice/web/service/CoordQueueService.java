package com.example.weatherconditionservice.web.service;

import com.example.weatherconditionservice.common.dto.QueueCoordRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class CoordQueueService implements ICoordQueueService
{
    private final AmqpTemplate coordRequestQueueTemplate;

    public void sendCoordinateRequestForCity(String cityName, LocalDate startDate, LocalDate endDate){
        QueueCoordRequestDto queueCoordRequestDto = new QueueCoordRequestDto(cityName, startDate, endDate);
        coordRequestQueueTemplate.convertAndSend(queueCoordRequestDto);
    }
}
