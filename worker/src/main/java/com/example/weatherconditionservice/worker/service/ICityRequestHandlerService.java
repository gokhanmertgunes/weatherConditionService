package com.example.weatherconditionservice.worker.service;

import com.example.weatherconditionservice.common.dto.QueueRequestDto;

public interface ICityRequestHandlerService
{
    void executeMessage(QueueRequestDto request);
}
