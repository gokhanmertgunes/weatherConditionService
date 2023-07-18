package com.example.weatherconditionservice.worker.converter;

import java.time.LocalDate;

public interface IUtcToUnixConverter
{
    long unixConverter(LocalDate date, int hour);
}
