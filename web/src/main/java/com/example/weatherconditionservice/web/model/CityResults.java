package com.example.weatherconditionservice.web.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
@Data
@Builder
public class CityResults
{
    private LocalDate date;
    private CityCategories categories;

    public CityResults(LocalDate date, CityCategories categories)
    {
        this.date = date;
        this.categories = categories;
    }
}
