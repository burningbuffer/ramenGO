package com.ramen.ramen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.ramen.ramen.model.AnswerModel;
import com.ramen.ramen.model.OrderModel;
import com.ramen.ramen.model.ProteinModel;
import com.ramen.ramen.repository.BrothRepository;
import com.ramen.ramen.repository.OrderRepository;
import com.ramen.ramen.repository.ProteinRerpository;

@Service
public class OrderService {

    @Autowired
    private AnswerModel answerModel;

    @Autowired
    private OrderRepository orderRepository;

        public ResponseEntity<?> placeOrder(OrderModel oModel){

            if(oModel.getBrothId() == 0 || oModel.getProteinId() == 0) {
                answerModel.setMessage("both brothId and proteinId are required");
                return new ResponseEntity<AnswerModel>(answerModel, HttpStatus.BAD_REQUEST);
            }
            else{
                return new ResponseEntity<OrderModel>(orderRepository.save(oModel), HttpStatus.CREATED);
            }
            
        }


}
