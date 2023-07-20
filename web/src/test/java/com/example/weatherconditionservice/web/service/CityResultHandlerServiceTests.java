package com.example.weatherconditionservice.web.service;

import com.example.weatherconditionservice.common.dto.CityCategoriesDto;
import com.example.weatherconditionservice.common.dto.CityDto;
import com.example.weatherconditionservice.common.dto.CityResultsDto;
import com.example.weatherconditionservice.web.model.City;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CityResultHandlerServiceTests
{
    @InjectMocks
    private CityResultHandlerService cityResultHandlerService;

    @Mock
    private ICityService cityService;

    @Captor
    private ArgumentCaptor<City> cityArgumentCaptor;

    @Test
    public void savePost()
    {
        CityDto city = CityDto.builder().city("Ankara").result(CityResultsDto.builder().date(LocalDate.of(2023,1,1)).categories(CityCategoriesDto.builder().co("Good").o3("Good").so2("Good").build()).build()).build();


        cityResultHandlerService.executeMessage(city);

        verify(cityService).saveCity(cityArgumentCaptor.capture());
        assertThat(cityArgumentCaptor.getValue().getCity()).isEqualTo("Ankara");
    }
}
