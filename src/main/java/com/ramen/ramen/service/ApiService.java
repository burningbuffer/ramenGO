package com.ramen.ramen.service;



import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public ApiService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public String postWithApiKey(String apiKey, String customUrl, String requestBody) {
        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        headers.set("Content-Type", "application/json");

        // Set request entity
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // Execute POST request
        ResponseEntity<String> response = restTemplate.exchange(customUrl, HttpMethod.POST, entity, String.class);


        JsonNode root;
        try {
            root = objectMapper.readTree(response.getBody());
            System.out.println("Body here: "+root.path("orderId").asText());
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
           
        // get orderId to transform into an Id
        //String a = response.getBody().toString();

       

        // Check response status code and return body
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            throw new RuntimeException("Request failed with status code: " + response.getStatusCode());
        }
    }
}