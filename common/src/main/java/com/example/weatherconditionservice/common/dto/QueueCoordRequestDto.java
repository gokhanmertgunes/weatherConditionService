package com.example.weatherconditionservice.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QueueCoordRequestDto
{
    private String city;
    private LocalDate startDate, endDate;
}
