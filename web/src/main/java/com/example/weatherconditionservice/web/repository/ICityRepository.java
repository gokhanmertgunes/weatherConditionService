package com.example.weatherconditionservice.web.repository;
import com.example.weatherconditionservice.web.model.City;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ICityRepository extends MongoRepository<City,String>
{
    boolean existsByCityAndResult_Date(String name, LocalDate date);
    List<City> findByCityAndResult_DateBetween(String city, Date startDate, Date endDate);

}
