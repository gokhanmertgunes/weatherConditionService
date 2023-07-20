package com.example.weatherconditionservice.web.service;


import com.example.weatherconditionservice.web.model.City;
import com.example.weatherconditionservice.web.repository.ICityRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@Service
public class CityService implements ICityService
{
    private final ICityRepository cityRepository;
   public void saveCity(City city){ cityRepository.insert(city); }

    public List<City> getAllCityInformationBetweenDates(String city, LocalDate startDate, LocalDate endDate)
    {
        Date startOfDay = Date.from(startDate.minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return cityRepository.findByCityAndResult_DateBetween(city, startOfDay, endOfDay);
    }

}


