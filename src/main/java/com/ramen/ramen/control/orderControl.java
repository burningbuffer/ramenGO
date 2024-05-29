package com.ramen.ramen.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ramen.ramen.model.OrderModel;
import com.ramen.ramen.service.BrothService;
import com.ramen.ramen.service.OrderService;
import com.ramen.ramen.service.ProteinService;

@RestController
@CrossOrigin(origins = "*")
public class orderControl {
    
    @Autowired
    private ProteinService proteinService;

    @Autowired
    private BrothService brothService;

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<?> placeOrder(@RequestBody OrderModel oModel){
        return orderService.placeOrder(oModel);
    }


}
