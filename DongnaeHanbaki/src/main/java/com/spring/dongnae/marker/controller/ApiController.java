package com.spring.dongnae.marker.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ApiController {

    @Autowired
    private ApiService apiService;

    @PostMapping("/disaster")
    @ResponseBody
    public JsonNode getDisasterData() {
        try {
            return apiService.getDisasterData(); // ApiService를 통해 데이터를 가져옴
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error fetching data");
        }
    }

    @PostMapping("/result")
    @ResponseBody
    public String handleResultPostRequest(@RequestBody String jsonData) {
        System.out.println("Received JSON data: " + jsonData);
        return "map/result"; // POST 요청에 대한 처리 결과 반환
    }
}

