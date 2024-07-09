package com.ramen.ramen.service;

import java.io.IOException;
import org.springframework.http.ResponseEntity;

public interface InterfaceApiService {

    ResponseEntity<?> postWithApiKey(String apiKey, String customUrl, String requestBody) throws IOException;

    ResponseEntity<?> getBrothsWithApiKey(String apiKey, String customUrl) throws IOException;

    ResponseEntity<?> getProteinsWithApiKey(String apiKey, String customUrl) throws IOException;
    
}
