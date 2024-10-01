package com.example.service;

import com.example.domain.Meal;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Collections;
import java.util.Map;

@Service
public class MealApiService {
    private final String API_URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public MealApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public List<Meal> getRecipeByName(String query) {
        String url = API_URL + query;
        String response = restTemplate.getForObject(url, String.class);
        
        try {
            // Parsitaan ainesosat vastauksesta
            Map<String, List<Meal>> responseMap = objectMapper.readValue(response, new TypeReference<Map<String, List<Meal>>>() {});
            return responseMap.get("meals");
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}


