package com.example.weatherconditionservice.worker.client;

import com.example.weatherconditionservice.common.dto.CityCoordinatesDto;
import com.example.weatherconditionservice.worker.converter.ConcentrationHandler;
import com.example.weatherconditionservice.worker.converter.IUtcToUnixConverter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;


import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class CrawlerClientTests
{
    @InjectMocks
    private CrawlerClient crawlerClient;

    @Mock
    private HttpRequestExecutor httpRequestExecutor;
    @Mock
    private IUtcToUnixConverter unixConverter;

    private String apiKey = "********************************";

    @Test
    public void test_fetchCoordinateInformation() throws JsonProcessingException
    {
        String api_url = "https://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s";
        String city = "Ankara";
        String url = String.format(api_url, city, apiKey);

        CityCoordinatesDto coordinates = CityCoordinatesDto.builder().lon(32.8543).lat(39.9199).build();

        RestTemplate restTemplate = new RestTemplate();
        String jsonResult = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(jsonResult);
        double lon = jsonResponse.path("coord").path("lon").asDouble();
        double lat = jsonResponse.path("coord").path("lat").asDouble();

        assertEquals(coordinates.getLat(), lat);
        assertEquals(coordinates.getLon(), lon);
    }
    @Test
    public void test_fetchWeatherInformation() throws JsonProcessingException
    {
        ConcentrationHandler concentrationHandler = new ConcentrationHandler();
        String api_url = "https://api.openweathermap.org/data/2.5/air_pollution/history?lat=%s8&lon=%s&start=%s&end=%s&appid=%s";
        Double lat = 39.9199;
        Double lon = 32.8543;
        long startUnix = 1678876800;
        long endUnix = 1678963199;
        String url = String.format(api_url, lat, lon, startUnix, endUnix, apiKey);

        RestTemplate restTemplate = new RestTemplate();
        String jsonResult = restTemplate.getForObject(url, String.class);

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonResponse = objectMapper.readTree(jsonResult);
        double co = jsonResponse.path("list").get(0).path("components").path("co").asDouble();
        double o3 = jsonResponse.path("list").get(0).path("components").path("o3").asDouble();
        double so2 = jsonResponse.path("list").get(0).path("components").path("so2").asDouble();

        assertEquals(concentrationHandler.coConcentrationHandler(co), "Good");
        assertEquals(concentrationHandler.so2ConcentrationHandler(so2), "Good");
        assertEquals(concentrationHandler.o3ConcentrationHandler(o3), "Fair");
    }
}
