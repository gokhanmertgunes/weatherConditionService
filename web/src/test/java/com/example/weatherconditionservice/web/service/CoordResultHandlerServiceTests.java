package com.example.weatherconditionservice.web.service;

import com.example.weatherconditionservice.common.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CoordResultHandlerServiceTests
{
    @InjectMocks
    private CoordResultHandlerService coordResultHandlerService;

    @Mock
    private ICityQueueService cityQueueService;

    @Test
    public void test_ExecuteMessage() {
        CityCoordinatesDto coordinatesDto = new CityCoordinatesDto(50, 50);
        QueueCoordResultDto coordResultDto = new QueueCoordResultDto("Ankara", LocalDate.of(2023, 1, 1), LocalDate.of(2023, 1, 2), coordinatesDto);

        coordResultHandlerService.executeMessage(coordResultDto);

        verify(cityQueueService).sendCityRequestForCityAndDateBetween(
                eq("Ankara"),
                eq(coordinatesDto),
                eq(LocalDate.of(2023, 1, 1)),
                eq(LocalDate.of(2023, 1, 2))
        );
    }
}
