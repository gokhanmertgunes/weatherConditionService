package com.example.weatherconditionservice.worker.client;


import com.example.weatherconditionservice.common.dto.CityCategoriesDto;
import com.example.weatherconditionservice.common.dto.CityCoordinatesDto;
import com.example.weatherconditionservice.worker.converter.IConcentrationHandler;
import com.example.weatherconditionservice.worker.converter.IUtcToUnixConverter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CrawlerClient implements ICrawlerClient
{
    public static final String API_COORDINATE_URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";
    public static final String API_WEATHER_URL = "https://api.openweathermap.org/data/2.5/air_pollution/history?lat=%s8&lon=%s&start=%s&end=%s&appid=%s";

    @Value("${weatherinformation.api.key}")
    private String apiKey;

    private final IHttpRequestExecutor httpRequestExecutor;
    private final IUtcToUnixConverter unixToUtcConverter;
    private final IConcentrationHandler concentrationHandler;

    @Override
    public CityCoordinatesDto fetchCoordinateInformation(String city) {
        String apiUrl = String.format(API_COORDINATE_URL, city, apiKey);

        String jsonResult = httpRequestExecutor.performApiCall(apiUrl);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonResponse = objectMapper.readTree(jsonResult);

            double lon = jsonResponse.path("coord").path("lon").asDouble();
            double lat = jsonResponse.path("coord").path("lat").asDouble();

            return CityCoordinatesDto.builder().lat(lat).lon(lon).build();
        } catch (Exception e) {
            throw new IllegalStateException("Error occured in reading the City Coordinates JSON object");
        }
    }

    @Override
    public CityCategoriesDto fetchWeatherInformation(Double lat, Double lon, LocalDate date)
    {
        long startUnix = unixToUtcConverter.unixConverter(date, 0);
        long endUnix = unixToUtcConverter.unixConverter(date, 59);

        String apiUrl = String.format(API_WEATHER_URL, lat, lon, startUnix, endUnix, apiKey);
        String jsonResult = httpRequestExecutor.performApiCall(apiUrl);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            JsonNode jsonResponse = objectMapper.readTree(jsonResult);
            double co = jsonResponse.path("list").get(0).path("components").path("co").asDouble();
            double o3 = jsonResponse.path("list").get(0).path("components").path("o3").asDouble();
            double so2 = jsonResponse.path("list").get(0).path("components").path("so2").asDouble();

            return CityCategoriesDto
                    .builder()
                    .co(concentrationHandler.coConcentrationHandler(co))
                    .o3(concentrationHandler.o3ConcentrationHandler(o3))
                    .so2(concentrationHandler.so2ConcentrationHandler(so2))
                    .build();

        } catch (Exception e) {
            throw new IllegalStateException("Error occured in reading the Weather Concentration JSON object");
        }
    }
}
