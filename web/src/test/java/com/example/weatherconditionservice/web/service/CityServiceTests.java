package com.example.weatherconditionservice.web.service;

import com.example.weatherconditionservice.web.model.City;
import com.example.weatherconditionservice.web.repository.ICityRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;


@DataMongoTest
public class CityServiceTests
{
    @Autowired
    private ICityService cityService;

    @Autowired
    private ICityRepository cityRepository;

    @Test
    public void getAllCityInformationBetweenDates() {
        LocalDate startDate = LocalDate.of(2023,7,17);
        LocalDate endDate = LocalDate.of(2023,7,19);

        cityService.getAllCityInformationBetweenDates("Antalya",startDate,endDate);

        Date startOfDay = Date.from(startDate.minusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endOfDay = Date.from(endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        List<City> list = cityRepository.findByCityAndResult_DateBetween("Antalya",startOfDay,endOfDay);
        assertThat(list).hasSize(3);
    }
}
