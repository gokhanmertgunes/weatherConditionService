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
   // private final ICrawlerClient crawlerClient;

   public void saveCity(City city){ cityRepository.insert(city); }

    public List<City> getAllCityInformationBetweenDates(String city, LocalDate startDate, LocalDate endDate)
    {
        Date startOfDay = Date.from(startDate.minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return cityRepository.findByCityAndResult_DateBetween(city, startOfDay, endOfDay);
    }

}
//    public void fetchWeatherInformation(String cityName, LocalDate startDate, LocalDate endDate)
//    {
//        if (startDate == null && endDate == null) {
//            endDate = LocalDate.now();
//            startDate = endDate.minusWeeks(1);
//        }
//
//        CityCoordinates coordinates = crawlerClient.CoordinateInformation(cityName);
//
//
//        Stream.iterate(startDate, date -> date.plusDays(1))
//                .limit(ChronoUnit.DAYS.between(startDate, endDate.plusDays(1)))
//                .forEach(date -> {
//                    if(!cityRepository.existsByCityAndResult_Date(cityName,date)) {
//                        CityCategories cityCategories = crawlerClient.WeatherInformation(coordinates.getLat(),coordinates.getLon(), date);
//                        City city = City.builder()
//                                .city(cityName)
//                                .result(CityResults.builder()
//                                        .date(date)
//                                        .categories(cityCategories)
//                                        .build())
//                                .build();
//
//                        cityRepository.insert(city);
//                    }
//                });
//    }


