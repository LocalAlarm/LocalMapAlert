package com.spring.dongnae.marker.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

@Service
public class ApiService {

    public JsonNode getDisasterData() throws Exception {
        String dataName = "DSSP-IF-00247";
        String serviceKey = "96757NRR7YMWSC7I";
        String pageNo = "1";
        String numOfRows = "10";

        StringBuilder urlBuilder = new StringBuilder("https://www.safetydata.go.kr/V2/api/");
        urlBuilder.append(URLEncoder.encode(dataName, "UTF-8"));
        urlBuilder.append("?" + "serviceKey=" + URLEncoder.encode(serviceKey, "UTF-8"));
        urlBuilder.append("&" + "pageNo=" + URLEncoder.encode(pageNo, "UTF-8"));
        urlBuilder.append("&" + "numOfRows=" + URLEncoder.encode(numOfRows, "UTF-8"));

        URI uri = new URI(urlBuilder.toString());
        URL url = uri.toURL();

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader;

        connection.connect();
        if (connection.getResponseCode() >= 200 && connection.getResponseCode() < 300) {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
        }

        StringBuilder sb = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        reader.close();
        connection.disconnect();

        // JSON 데이터를 객체로 매핑하여 필요한 값을 추출
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(sb.toString()); // JSON 데이터를 JsonNode로 파싱

        // totalCount 값을 추출
        int totalCount = rootNode.path("totalCount").asInt();

        // body에 포함된 데이터들을 반환
        JsonNode body = rootNode.path("body");

        return body;
    }
}
