package com.ramen.ramen.service;

import java.io.IOException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramen.ramen.model.BrothModel;
import com.ramen.ramen.model.ProteinModel;

@Service
public class ApiService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    BrothModel brothModel;
    ProteinModel proteinModel;

    public ApiService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public void print(String string){
        System.out.println(string);
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

    public ResponseEntity<?> getBrothsWithApiKey(String apiKey, String customUrl) throws IOException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        headers.set("Content-Type", "application/json");
 
        HttpEntity<String> entity = new HttpEntity<>(headers);
 
        ResponseEntity<String> response = restTemplate.exchange(customUrl, HttpMethod.GET, entity, String.class);
 
         // Check response status code and return body
        if (response.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<String>(response.getBody(), HttpStatus.CREATED);
        } else {
            throw new RuntimeException("Request failed with status code: " + response.getStatusCode());
        }
    }

    public ResponseEntity<?> getProteinsWithApiKey(String apiKey, String customUrl) throws IOException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        headers.set("Content-Type", "application/json");

        // Set request entity
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Execute GET request
        ResponseEntity<String> response = restTemplate.exchange(customUrl, HttpMethod.GET, entity, String.class);

        // Check response status code and return body
        if (response.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<String>(response.getBody(), HttpStatus.CREATED);
        } else {
            throw new RuntimeException("Request failed with status code: " + response.getStatusCode());
        }
           
   }
}