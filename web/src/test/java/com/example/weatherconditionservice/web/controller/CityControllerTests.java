package com.example.weatherconditionservice.web.controller;

import com.example.weatherconditionservice.web.model.City;
import com.example.weatherconditionservice.web.model.CityCategories;
import com.example.weatherconditionservice.web.model.CityResults;
import com.example.weatherconditionservice.web.service.CityService;
import com.example.weatherconditionservice.web.service.ICoordQueueService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CityController.class)
@ContextConfiguration(classes = CityController.class)
public class CityControllerTests extends ControllerTestsBase {
    @MockBean
    private ICoordQueueService coordQueueService;

    @MockBean
    private CityService cityService;

    @Test
    public void createCityQueueRequests_WithDates_Success() throws Exception
    {
        String city = "Antalya";
        LocalDate startDate = LocalDate.of(2023, 7, 15);
        LocalDate endDate = LocalDate.of(2023, 7, 17);

        City city1 = City.builder()
                .city(city)
                .result(CityResults.builder()
                        .date(LocalDate.of(2023, 7, 15))
                        .categories(CityCategories.builder()
                                .co("Good")
                                .o3("Fair")
                                .so2("Good")
                                .build())
                        .build())
                .build();

        City city2 = City.builder()
                .city(city)
                .result(CityResults.builder()
                        .date(LocalDate.of(2023, 7, 16))
                        .categories(CityCategories.builder()
                                .co("Good")
                                .o3("Good")
                                .so2("Good")
                                .build())
                        .build())
                .build();

        City city3 = City.builder()
                .city(city)
                .result(CityResults.builder()
                        .date(LocalDate.of(2023, 7, 17))
                        .categories(CityCategories.builder()
                                .co("Good")
                                .o3("Fair")
                                .so2("Good")
                                .build())
                        .build())
                .build();
        List<City> mockCityList = List.of(city1, city2, city3);
        Mockito.when(cityService.getAllCityInformationBetweenDates(city, startDate, endDate))
                .thenReturn(mockCityList);

        this.mockMvc.perform(get("/api/weather?city=Antalya&startDate=15-07-2023&endDate=17-07-2023"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].city").value(city))
                .andExpect(jsonPath("$[0].result.date").value("2023-07-15"))
                .andExpect(jsonPath("$[0].result.categories.co").value("Good"))
                .andExpect(jsonPath("$[0].result.categories.o3").value("Fair"))
                .andExpect(jsonPath("$[0].result.categories.so2").value("Good"))
                .andExpect(jsonPath("$[1].city").value(city))
                .andExpect(jsonPath("$[1].result.date").value("2023-07-16"))
                .andExpect(jsonPath("$[1].result.categories.co").value("Good"))
                .andExpect(jsonPath("$[1].result.categories.o3").value("Good"))
                .andExpect(jsonPath("$[1].result.categories.so2").value("Good"))
                .andExpect(jsonPath("$[2].city").value(city))
                .andExpect(jsonPath("$[2].result.date").value("2023-07-17"))
                .andExpect(jsonPath("$[2].result.categories.co").value("Good"))
                .andExpect(jsonPath("$[2].result.categories.o3").value("Fair"))
                .andExpect(jsonPath("$[2].result.categories.so2").value("Good"));
    }

}
