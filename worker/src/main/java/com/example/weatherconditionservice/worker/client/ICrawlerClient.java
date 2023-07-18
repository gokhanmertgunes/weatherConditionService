package com.example.weatherconditionservice.worker.client;

import com.example.weatherconditionservice.common.dto.CityCategoriesDto;
import com.example.weatherconditionservice.common.dto.CityCoordinatesDto;

import java.time.LocalDate;

public interface ICrawlerClient
{
    CityCoordinatesDto fetchCoordinateInformation(String city);

    CityCategoriesDto fetchWeatherInformation(Double lat, Double lon, LocalDate date);
}
