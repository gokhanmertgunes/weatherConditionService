package com.example.weatherconditionservice.web.service;

import java.time.LocalDate;

public interface ICoordQueueService
{
    void sendCoordinateRequestForCity(String cityName, LocalDate startDate, LocalDate endDate);
}
