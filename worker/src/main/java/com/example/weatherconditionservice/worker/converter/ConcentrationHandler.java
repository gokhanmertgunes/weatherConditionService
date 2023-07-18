package com.example.weatherconditionservice.worker.converter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConcentrationHandler implements IConcentrationHandler
{
    public String so2ConcentrationHandler(double so2) {
        if(so2 < 20) return "Good";
        else if(so2 < 80) return "Fair";
        else if(so2 < 250) return "Moderate";
        else if(so2 < 350) return "Poor";
        else return "Very Poor";
    }

    public String coConcentrationHandler(double co) {
        if(co < 4400) return "Good";
        else if(co < 9400) return "Fair";
        else if(co < 12400) return "Moderate";
        else if(co < 15400) return "Poor";
        else return "Very Poor";
    }

    public String o3ConcentrationHandler(double o3) {
        if(o3 < 60) return "Good";
        else if(o3 < 100) return "Fair";
        else if(o3 < 140) return "Moderate";
        else if(o3 < 180) return "Poor";
        else return "Very Poor";
    }
}
