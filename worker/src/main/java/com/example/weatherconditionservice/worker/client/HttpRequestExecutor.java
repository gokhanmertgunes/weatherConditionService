package com.example.weatherconditionservice.worker.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
@RequiredArgsConstructor
@Slf4j
public class HttpRequestExecutor implements IHttpRequestExecutor
{
    public String performApiCall(String apiUrl) {
        StringBuilder response = new StringBuilder();

        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
            } else {
                log.warn("Couldn't get successful result from http request status:{} url: {}", responseCode, url);
            }

            connection.disconnect();
        } catch (Exception e) {
            log.error("Unknown error occurred while executing http request for url: {}", apiUrl, e);

            throw new IllegalStateException("Unknown error occurred while executing http request", e);
        }

        return response.toString();
    }
}
