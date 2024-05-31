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

    @PostMapping("/orders")
    @ResponseBody
    public ResponseEntity<?> sendRequestWithApiKey(@RequestBody String requestBody) {
        try {
            // order logic


            return apiService.postWithApiKey(key, generateidUrl, requestBody);


        } catch (Exception e) {
            
            answerModel.setMessage(e.getMessage());
            return new ResponseEntity<AnswerModel>(answerModel, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/broths")
    public ResponseEntity<?> getRequestBrothsWithApiKey() {
        try {
            return apiService.getBrothsWithApiKey(key, listBrothsUrl);
        } catch (Exception e) {
            answerModel.setMessage(e.getMessage());
            return new ResponseEntity<AnswerModel>(answerModel, HttpStatus.BAD_REQUEST);

        }
    }

    @GetMapping("/proteins")
    public ResponseEntity<?> getRequestProteinsWithApiKey() {
        try {
            return apiService.getProteinsWithApiKey(key, listProteinsUrl);
        } catch (Exception e) {
            answerModel.setMessage(e.getMessage());
            return new ResponseEntity<AnswerModel>(answerModel, HttpStatus.BAD_REQUEST);
        }
    }

}

