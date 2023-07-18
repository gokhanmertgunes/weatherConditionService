package com.example.weatherconditionservice.web.model;

import lombok.Data;

import java.time.LocalDate;
@Data
public class CityIdentifier
{
    private String name;
    private LocalDate date;
}
