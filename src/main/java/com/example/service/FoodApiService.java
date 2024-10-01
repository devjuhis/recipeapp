package com.example.service;

import com.example.domain.ApiResponse;
import com.example.domain.Recipe;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class FoodApiService {

    //ei enää käytössä vanha api
    private static final Logger logger = LoggerFactory.getLogger(FoodApiService.class);

    @Value("${foodapi.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public FoodApiService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Recipe[] fetchRecipes(String query) {
        String url = "https://api.spoonacular.com/recipes/complexSearch?query=" + query + "&apiKey=" + apiKey;
        try {
            //(responseentity) response.getBody() -> resttemplate
            String response = restTemplate.getForObject(url, String.class);

            // Parse JSON response -> objectmapper
            ApiResponse apiResponse = objectMapper.readValue(response, ApiResponse.class);
            logger.info(response);
            return apiResponse.getResults();
        } catch (Exception e) {
            logger.error("Error fetching recipes from API", e);
            return new Recipe[0];
        }
    }
}

