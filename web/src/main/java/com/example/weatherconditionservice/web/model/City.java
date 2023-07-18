package com.example.weatherconditionservice.web.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@Builder
public class City
{
    private String city;
    private CityResults result;
}
