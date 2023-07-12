package com.sas.refactoring.weather.controller;

import com.sas.refactoring.weather.service.WeatherNowAPIService;
import com.sas.refactoring.weather.service.WeatherPredictAPIService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin
@RequestMapping("/weather")
@RequiredArgsConstructor
@Slf4j
public class WeatherController {
    private final WeatherPredictAPIService weatherPredictAPIService;
    private final WeatherNowAPIService weatherNowAPIService;
    @GetMapping("/predict")
    public Mono<String> getPredictWeather() {
        Mono<String> result = weatherPredictAPIService.fetchDataFromExternalAPI();
        log.info("예상 날씨 받아온 값 : {}", result);
        return result;
    }

    @GetMapping("/now")
    public Mono<String> getNowWeather() {
        Mono<String> result = weatherNowAPIService.fetchDataFromExternalAPI();
        log.info("현재 날씨 받아온 값 : {}", result);
        return result;
    }

}
