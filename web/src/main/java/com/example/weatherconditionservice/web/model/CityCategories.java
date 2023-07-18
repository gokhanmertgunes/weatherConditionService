package com.example.weatherconditionservice.web.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CityCategories
{
    private String co;
    private String o3;
    private String so2;

    public CityCategories(String co, String o3, String so2)
    {
        this.co = co;
        this.o3 = o3;
        this.so2 = so2;
    }
}
