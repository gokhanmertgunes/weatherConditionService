package com.example.weatherconditionservice.web.service;

import com.example.weatherconditionservice.common.dto.CityCoordinatesDto;

import java.time.LocalDate;

public interface ICityQueueService
{
    void sendCityRequestForCityAndDateBetween(String cityName, CityCoordinatesDto coord, LocalDate startDate, LocalDate endDate);
}
