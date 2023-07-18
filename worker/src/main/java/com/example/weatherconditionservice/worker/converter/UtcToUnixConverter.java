package com.example.weatherconditionservice.worker.converter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
@Service
@RequiredArgsConstructor
public class UtcToUnixConverter implements IUtcToUnixConverter
{
    public long unixConverter(LocalDate date, int minute) {
        LocalDateTime dateTime = date.atTime(0, minute).atZone(ZoneOffset.UTC).toLocalDateTime();
        return dateTime.toEpochSecond(ZoneOffset.UTC);
    }
}
