package com.example.weatherconditionservice.web.service;

import com.example.weatherconditionservice.common.dto.CityCoordinatesDto;
import com.example.weatherconditionservice.common.dto.QueueCoordRequestDto;
import com.example.weatherconditionservice.common.dto.QueueRequestDto;
import com.example.weatherconditionservice.web.model.CityCoordinates;
import com.example.weatherconditionservice.web.repository.ICityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CityQueueService implements ICityQueueService
{
    private final AmqpTemplate requestQueueTemplate;
    private final ICityRepository cityRepository;



    public void sendCityRequestForCityAndDateBetween(String cityName, CityCoordinatesDto coord, LocalDate startDate, LocalDate endDate){

        Stream<QueueRequestDto> queueRequestDtoStream = Stream.iterate(startDate, date -> date.plusDays(1))
                .limit(ChronoUnit.DAYS.between(startDate, endDate.plusDays(1)))
                .map(date -> new QueueRequestDto(cityName, date, coord));

        queueRequestDtoStream
                .filter(request -> !cityRepository.existsByCityAndResult_Date(request.getCity(),request.getDate()))
                .forEach(requestQueueTemplate::convertAndSend);
    }
}
