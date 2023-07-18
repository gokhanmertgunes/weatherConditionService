package com.example.weatherconditionservice.web.controller;

import com.example.weatherconditionservice.web.model.City;
import com.example.weatherconditionservice.web.service.CityService;
import com.example.weatherconditionservice.web.service.ICityQueueService;
import com.example.weatherconditionservice.web.service.ICoordQueueService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("api/weather")
@AllArgsConstructor
public class CityController
{
    private final CityService cityService;
    private final ICoordQueueService coordQueueService;
    @GetMapping
    public List<City> createCityQueueRequests(@RequestParam("city") String city,
                                             @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                                             @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate)
    {
        if (startDate == null && endDate == null) {
        endDate = LocalDate.now();
        startDate = endDate.minusWeeks(1);
        }

        coordQueueService.sendCoordinateRequestForCity(city,startDate,endDate);

        return cityService.getAllCityInformationBetweenDates(city,startDate,endDate);
    }








//
//    @GetMapping
//    public List<City> fetchWeatherInformation(@RequestParam("city") String city,
//                                              @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
//                                              @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {
//
//        cityService.fetchWeatherInformation(city, startDate, endDate);
//
//        return cityService.getAllCityInformationBetweenDates(city,startDate,endDate);
//    }
}
