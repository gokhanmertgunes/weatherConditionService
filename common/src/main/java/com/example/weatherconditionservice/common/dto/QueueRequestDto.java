package com.example.weatherconditionservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QueueRequestDto
{
    private String city;
    private LocalDate date;
    private CityCoordinatesDto coord;
}
