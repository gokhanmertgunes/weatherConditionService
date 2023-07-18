package com.example.weatherconditionservice.worker.service;

import com.example.weatherconditionservice.common.dto.*;
import com.example.weatherconditionservice.worker.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityRequestHandlerService implements ICityRequestHandlerService
{
    private final AmqpTemplate resultQueueTemplate;

    private final ICrawlerClient crawlerClient;

    @Override
    public void executeMessage(QueueRequestDto request)
    {
        CityCoordinatesDto coordinatesDto = crawlerClient.fetchCoordinateInformation(request.getCity());
        CityCategoriesDto categoriesDto = crawlerClient.fetchWeatherInformation(coordinatesDto.getLat(), coordinatesDto.getLon(), request.getDate());
        CityDto city = CityDto.builder()
                .city(request.getCity())
                .result(CityResultsDto.builder()
                        .date(request.getDate())
                        .categories(CityCategoriesDto.builder()
                                .so2(categoriesDto.getSo2())
                                .o3(categoriesDto.getO3())
                                .co(categoriesDto.getCo())
                                .build())
                        .build())
                .build();

        resultQueueTemplate.convertAndSend(city);
    }
}
