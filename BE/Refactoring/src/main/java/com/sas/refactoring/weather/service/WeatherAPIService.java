package com.sas.refactoring.weather.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Service
public class WeatherAPIService {
    private final WebClient webClient;

    public WeatherAPIService() {
        this.webClient = WebClient.create();
    }

    //Mono : 스프링 WebFlux에서 사용되는 리액티브 타입. 0 또는 1개의 요소를 포함하는 단일 결과
    public Mono<String> fetchDataFromExternalAPI() {
//        String baseUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst"+"?";
//        String serviceKey ="serviceKey=" + "01hXuOcwgTo8a6g4AIpQLW7RYpmLcATl2OcGuzUHEc4oYQ1sSXd7b1etdhb908cP4QZUlqAWz4O%2BmoUQH1o2kg%3D%3D" + "&";
//        String pageNo = "pageNo=" + "1" + "&";
//        String numOfRows = "numOfRows=" + "10" + "&";
//        String dataType = "dataType=JSON" + "&";
//        String baseDate = "base_date=" + "20230711" + "&";
//        String baseTime = "base_time=" + "0630" + "&";
//        String nx = "nx=" + "55" + "&";
//        String ny = "ny=" + "127";

        String baseUrl = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst";
        String pageNo = "1";
        String numOfRows = "10";
        String dataType = "JSON";
        String baseDate = "20230711";
        String baseTime = "0630";
        String nx = "55";
        String ny = "127";

        String serviceKey = "01hXuOcwgTo8a6g4AIpQLW7RYpmLcATl2OcGuzUHEc4oYQ1sSXd7b1etdhb908cP4QZUlqAWz4O%2BmoUQH1o2kg%3D%3D";
        String correctedServiceKey = serviceKey.replace("+", "%2B");

        String mainUrl = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("serviceKey", correctedServiceKey)
                .queryParam("pageNo", pageNo)
                .queryParam("numOfRows", numOfRows)
                .queryParam("dataType", dataType)
                .queryParam("base_date", baseDate)
                .queryParam("base_time", baseTime)
                .queryParam("nx", nx)
                .queryParam("ny", ny)
                .build().toUriString();


        Mono<String> response = webClient.get()
                .uri(mainUrl)
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> System.out.println("Error occurred: " + error.getMessage()));
        System.out.println("url : " + mainUrl);
        // 받아온 데이터 처리 로직
        /*
        response = Mono 객체. 해당 객체는 리액티브 프로그래밍에서 사용하는 비동기 데이터 스트림
        마치 FE 비동기 통신의 Promise 객체와 유사하다고 볼 수 있다.
        Mono의 subcribe나 block 메서드(현재 스레드 중단하고 값을 얻을 때까지 대기)를 사용할 것.
        subcribe : onNext, onError, onComplete의 3개의 콜백 함수를 인수로 받을 수 있음.
         */

        //System.out.println(response.block()); <-- 스레드 중지하고 값을 받아옴. 권장되지 않음
        System.out.println(response.subscribe(result -> System.out.println(result)));
        return response;
    }
}