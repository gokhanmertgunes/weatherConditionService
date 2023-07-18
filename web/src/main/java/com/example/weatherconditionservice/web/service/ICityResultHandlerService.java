package com.example.weatherconditionservice.web.service;

import com.example.weatherconditionservice.common.dto.CityDto;

public interface ICityResultHandlerService
{
    void executeMessage(CityDto cityDto);
}
