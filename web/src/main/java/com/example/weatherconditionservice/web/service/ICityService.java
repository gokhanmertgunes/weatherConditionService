package com.example.weatherconditionservice.web.service;

import com.example.weatherconditionservice.web.model.City;

import java.time.LocalDate;
import java.util.List;

public interface ICityService
{
   //void fetchWeatherInformation(String city, LocalDate startDate, LocalDate endDate);

    void saveCity(City city);

    List<City> getAllCityInformationBetweenDates(String city, LocalDate startDate, LocalDate endDate);
}
