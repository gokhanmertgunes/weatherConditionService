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
public class QueueCoordRequestDto
{
    private String city;
    private LocalDate startDate, endDate;
}
