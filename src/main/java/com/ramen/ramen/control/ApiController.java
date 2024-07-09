package com.ramen.ramen.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramen.ramen.model.AnswerModel;
import com.ramen.ramen.service.ApiService;

@Controller
@CrossOrigin(origins = "*")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private AnswerModel answerModel;

    // i could put these in application.properties but i decided to leave hardcoded like this
    private final String generateidUrl = "https://api.tech.redventures.com.br/orders/generate-id";
    private final String listBrothsUrl = "https://api.tech.redventures.com.br/broths";
    private final String listProteinsUrl = "https://api.tech.redventures.com.br/proteins";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/orders")
    @ResponseBody
    public ResponseEntity<?> sendOrderWithApiKey(@RequestBody String requestBody, @RequestHeader("x-api-key") String key) {
        try {

            if(key.isEmpty()){
                answerModel.setMessage("x-api-key is missing");
                return new ResponseEntity<AnswerModel>(answerModel, HttpStatus.FORBIDDEN);
            }

            JsonNode requestBodyRoot = objectMapper.readTree(requestBody);

            if(requestBody == null || requestBody.trim().isEmpty() || requestBodyRoot.path("brothId").asText().isEmpty() || requestBodyRoot.path("proteinId").asText().isEmpty()) {
                answerModel.setMessage("both brothId and proteinId are required");
                return new ResponseEntity<>(answerModel, HttpStatus.BAD_REQUEST);
            }
            
            return apiService.postWithApiKey(key, generateidUrl, requestBody);

        } catch (Exception e) {
            
            if(key.isEmpty()){
                answerModel.setMessage("x-api-key is missing");
                return new ResponseEntity<AnswerModel>(answerModel, HttpStatus.FORBIDDEN);
            }else{
                answerModel.setMessage("could not place order");
                return new ResponseEntity<>(answerModel, HttpStatus.INTERNAL_SERVER_ERROR);
            }

           
        }
    }
    
    @GetMapping("/broths")
    public ResponseEntity<?> getBrothsListWithApiKey(@RequestHeader("x-api-key") String key) {

        if(key.isEmpty()){
            answerModel.setMessage("x-api-key is missing");
            return new ResponseEntity<AnswerModel>(answerModel, HttpStatus.FORBIDDEN);
        }

        try {

            return apiService.getBrothsWithApiKey(key, listBrothsUrl);

        } catch (Exception e) {
            
            answerModel.setMessage("x-api-key is missing");
            return new ResponseEntity<AnswerModel>(answerModel, HttpStatus.FORBIDDEN);

        }
    }

    @GetMapping("/proteins")
    public ResponseEntity<?> getProteinsListWithApiKey(@RequestHeader("x-api-key") String key) {
        
        if(key.isEmpty()){
            answerModel.setMessage("x-api-key is missing");
            return new ResponseEntity<AnswerModel>(answerModel, HttpStatus.FORBIDDEN);
        }

        try {

            return apiService.getProteinsWithApiKey(key, listProteinsUrl);

        } catch (Exception e) {
            answerModel.setMessage("x-api-key is missing");
            return new ResponseEntity<AnswerModel>(answerModel, HttpStatus.FORBIDDEN);
        }
    }

}

