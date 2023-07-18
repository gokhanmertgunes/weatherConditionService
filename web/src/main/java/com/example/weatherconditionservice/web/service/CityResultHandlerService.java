package com.example.weatherconditionservice.web.service;

import com.example.weatherconditionservice.common.dto.CityDto;
import com.example.weatherconditionservice.web.model.City;
import com.example.weatherconditionservice.web.model.CityCategories;
import com.example.weatherconditionservice.web.model.CityResults;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CityResultHandlerService implements ICityResultHandlerService
{
    private final ICityService cityService;
    @Override
    public void executeMessage(CityDto cityDto) { cityService.saveCity(convertFrom(cityDto)); }

    private City convertFrom(CityDto cityDto)
    {
        return City.builder()
                .city(cityDto.getCity())
                .result(CityResults.builder()
                        .date(cityDto.getResult().getDate())
                        .categories(CityCategories.builder()
                                .o3(cityDto.getResult().getCategories().getO3())
                                .so2(cityDto.getResult().getCategories().getSo2())
                                .co(cityDto.getResult().getCategories().getCo())
                                .build())
                        .build())
                .build();
    }
}
