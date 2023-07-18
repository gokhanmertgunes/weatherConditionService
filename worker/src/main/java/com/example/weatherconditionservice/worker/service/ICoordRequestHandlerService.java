package com.example.weatherconditionservice.worker.service;

import com.example.weatherconditionservice.common.dto.QueueCoordRequestDto;
import com.example.weatherconditionservice.common.dto.QueueRequestDto;

public interface ICoordRequestHandlerService
{
    void executeMessage(QueueCoordRequestDto request);

}
