package com.ramen.ramen.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.ramen.ramen.model.AnswerModel;
import com.ramen.ramen.model.BrothModel;
import com.ramen.ramen.model.OrderModel;
import com.ramen.ramen.model.ProteinModel;

@Service
public class ApiService {

    private final String listBrothsUrl = "https://api.tech.redventures.com.br/broths";
    private final String listProteinsUrl = "https://api.tech.redventures.com.br/proteins";
    private final String imageRamenUrl = "https://tech.redventures.com.br/icons/ramen/ramen";

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private AnswerModel answerModel;
    
    public ApiService() {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
    }

    public void print(String string){
        System.out.println(string);
    }

    public ResponseEntity<?> postWithApiKey(String apiKey, String customUrl, String requestBody) throws IOException {

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        HttpEntity<String> bentity = new HttpEntity<>(headers);
        HttpEntity<String> pentity = new HttpEntity<>(headers);

        ResponseEntity<String> OrderResponse = restTemplate.exchange(customUrl, HttpMethod.POST, entity, String.class);

        if (!OrderResponse.getStatusCode().is2xxSuccessful()) {
            
            answerModel.setMessage("could not place order");
            return new ResponseEntity<>(answerModel, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ResponseEntity<String> BrothResponse = restTemplate.exchange(listBrothsUrl, HttpMethod.GET, bentity, String.class);

        if (!BrothResponse.getStatusCode().is2xxSuccessful()) {

            answerModel.setMessage("could not place order");
            return new ResponseEntity<>(answerModel, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        ResponseEntity<String> ProteinResponse = restTemplate.exchange(listProteinsUrl, HttpMethod.GET, pentity, String.class);

        if (!ProteinResponse.getStatusCode().is2xxSuccessful()) {

            answerModel.setMessage("could not place order");
            return new ResponseEntity<>(answerModel, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        List<BrothModel> broths = new ArrayList<>();
        List<ProteinModel> proteins = new ArrayList<>();

        OrderModel orderModel = new OrderModel();

        JsonNode root;
        JsonNode bRoot;
        JsonNode pRoot;
        JsonNode orderRoot;

        try {

            root = objectMapper.readTree(OrderResponse.getBody());
            bRoot = objectMapper.readTree(BrothResponse.getBody());
            pRoot = objectMapper.readTree(ProteinResponse.getBody());
            orderRoot = objectMapper.readTree(entity.getBody());

            if (bRoot.isArray()) {
                ArrayNode arrayNode = (ArrayNode) bRoot;
                for (JsonNode node : arrayNode) {
                    BrothModel brothModel = objectMapper.treeToValue(node, BrothModel.class);
                    broths.add(brothModel);
                }
            }

            if (pRoot.isArray()) {
                ArrayNode arrayNode = (ArrayNode) pRoot;
                for (JsonNode node : arrayNode) {
                    ProteinModel proteinModel = objectMapper.treeToValue(node, ProteinModel.class);
                    proteins.add(proteinModel);
                }
            }

            int bindexId = Integer.parseInt(orderRoot.path("brothId").asText());
            int pindexId = Integer.parseInt(orderRoot.path("proteinId").asText());

            String bName = broths.get(bindexId - 1).getName();
            String pName = proteins.get(pindexId - 1).getName();

            orderModel.setId(root.path("orderId").asText());
            orderModel.setDescription(bName+" and "+pName);
            orderModel.setImage(imageRamenUrl+pName+".png");
           
            if (OrderResponse.getStatusCode().is2xxSuccessful()) {
                return new ResponseEntity<OrderModel>(orderModel, HttpStatus.OK);
            } else {
                answerModel.setMessage("could not place order");
                return new ResponseEntity<>(answerModel, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (JsonMappingException e) {
            answerModel.setMessage("Invalid JSON structure");
            return new ResponseEntity<>(answerModel, HttpStatus.BAD_REQUEST);
        } catch (IOException e) {
            answerModel.setMessage("could not place order");
            return new ResponseEntity<>(answerModel, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public ResponseEntity<?> getBrothsWithApiKey(String apiKey, String customUrl) throws IOException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        headers.set("Content-Type", "application/json");
 
        HttpEntity<String> entity = new HttpEntity<>(headers);
 
        ResponseEntity<String> response = restTemplate.exchange(customUrl, HttpMethod.GET, entity, String.class);
 
        List<BrothModel> broths = new ArrayList<>();

        try {

            JsonNode root = objectMapper.readTree(response.getBody());
            if (root.isArray()) {
                ArrayNode arrayNode = (ArrayNode) root;
                
                for (JsonNode node : arrayNode) {
                    BrothModel brothModel = new BrothModel();
                    brothModel.setId(node.path("id").asText());
                    brothModel.setImageInactive(node.path("imageInactive").asText());
                    brothModel.setImageActive(node.path("imageActive").asText());
                    brothModel.setName(node.path("name").asText());
                    brothModel.setDescription(node.path("description").asText());
                    print(node.path("description").asText());
                    brothModel.setPrice(node.path("price").asText());
                    broths.add(brothModel);
                }
                return new ResponseEntity<Iterable<BrothModel>>(broths, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Expected an array in the JSON response", HttpStatus.BAD_REQUEST);
            }

        } catch (JsonMappingException e) {
            answerModel.setMessage("Invalid JSON structure");
            return new ResponseEntity<>(answerModel, HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<Iterable<BrothModel>>(broths, HttpStatus.CREATED);
        } else {
            answerModel.setMessage("could not place order");
            return new ResponseEntity<>(answerModel, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getProteinsWithApiKey(String apiKey, String customUrl) throws IOException {
        
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", apiKey);
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(customUrl, HttpMethod.GET, entity, String.class);

        List<ProteinModel> proteins = new ArrayList<>();

        try {

            JsonNode root = objectMapper.readTree(response.getBody());
            if (root.isArray()) {
                ArrayNode arrayNode = (ArrayNode) root;
                
                for (JsonNode node : arrayNode) {
                    ProteinModel proteinModel = new ProteinModel();
                    proteinModel.setId(node.path("id").asText());
                    proteinModel.setImageInactive(node.path("imageInactive").asText());
                    proteinModel.setImageActive(node.path("imageActive").asText());
                    proteinModel.setName(node.path("name").asText());
                    proteinModel.setDescription(node.path("description").asText());
                    print(node.path("description").asText());
                    proteinModel.setPrice(node.path("price").asText());
                    proteins.add(proteinModel);
                }
                
                return new ResponseEntity<Iterable<ProteinModel>>(proteins, HttpStatus.OK);

            } else {
                answerModel.setMessage("Invalid JSON structure");
                return new ResponseEntity<>(answerModel, HttpStatus.BAD_REQUEST);
            }

        } catch (JsonMappingException e) {
            answerModel.setMessage("Invalid JSON structure");
            return new ResponseEntity<>(answerModel, HttpStatus.BAD_REQUEST);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        if (response.getStatusCode().is2xxSuccessful()) {
            return new ResponseEntity<Iterable<ProteinModel>>(proteins, HttpStatus.CREATED);
        } else {
            answerModel.setMessage("could not place order");
            return new ResponseEntity<>(answerModel, HttpStatus.INTERNAL_SERVER_ERROR);
        }
           
   }
}