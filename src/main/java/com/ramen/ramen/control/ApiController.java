package com.ramen.ramen.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ramen.ramen.model.AnswerModel;
import com.ramen.ramen.service.ApiService;

@Controller
public class ApiController {

    @Autowired
    private ApiService apiService;

    @Autowired
    private AnswerModel answerModel;

    @Value("${x-api-key}")
    private String key;

    private final String generateidUrl = "https://api.tech.redventures.com.br/orders/generate-id";
    private final String listBrothsUrl = "https://api.tech.redventures.com.br/broths";
    private final String listProteinsUrl = "https://api.tech.redventures.com.br/proteins";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("/orders")
    @ResponseBody
    public ResponseEntity<?> sendRequestWithApiKey(@RequestBody String requestBody) {
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
    public ResponseEntity<?> getRequestBrothsWithApiKey() {

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
    public ResponseEntity<?> getRequestProteinsWithApiKey() {
        
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

