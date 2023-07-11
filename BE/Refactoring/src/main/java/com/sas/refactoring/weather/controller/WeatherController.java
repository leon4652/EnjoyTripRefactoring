package com.sas.refactoring.weather.controller;

import com.sas.refactoring.weather.service.WeatherAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/weather")
@RequiredArgsConstructor
@Slf4j
public class WeatherController {
    private final WeatherAPIService weatherAPIService;

    @GetMapping
    public void getWeather() {
        log.info("날씨 받아온 값 : {}", weatherAPIService.fetchDataFromExternalAPI());
    }

}
