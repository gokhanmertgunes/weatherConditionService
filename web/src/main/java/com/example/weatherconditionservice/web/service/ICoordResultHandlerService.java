package com.example.weatherconditionservice.web.service;

import com.example.weatherconditionservice.common.dto.CityCoordinatesDto;
import com.example.weatherconditionservice.common.dto.CityDto;
import com.example.weatherconditionservice.common.dto.QueueCoordResultDto;

public interface ICoordResultHandlerService
{

    void executeMessage(QueueCoordResultDto coord);
}
