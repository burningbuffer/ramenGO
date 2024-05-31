package com.ramen.ramen.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ramen.ramen.service.ApiService;

@Controller
public class ApiController {

    @Autowired
    private ApiService apiService;

    @Value("${x-api-key}")
    private String key;

    private final String customUrl = "https://api.tech.redventures.com.br/orders/generate-id";

    @PostMapping("/orders")
    @ResponseBody
    public String sendRequestWithApiKey(@RequestBody String requestBody) {
        try {
            return apiService.postWithApiKey(key, customUrl, requestBody);
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}

