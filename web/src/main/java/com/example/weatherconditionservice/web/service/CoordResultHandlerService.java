package com.example.weatherconditionservice.web.service;

import com.example.weatherconditionservice.common.dto.QueueCoordResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CoordResultHandlerService implements ICoordResultHandlerService
{
    private final ICityQueueService cityQueueService;
    @Override
    public void executeMessage(QueueCoordResultDto coord) {
        cityQueueService.sendCityRequestForCityAndDateBetween(coord.getCity(), coord.getCoordinates(), coord.getStartDate(), coord.getEndDate());
    }
}
