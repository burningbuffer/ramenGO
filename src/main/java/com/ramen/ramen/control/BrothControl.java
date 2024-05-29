package com.ramen.ramen.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.ramen.ramen.model.BrothModel;
import com.ramen.ramen.service.BrothService;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@CrossOrigin(origins = "*")
public class BrothControl {
    
    @Autowired
    private BrothService service;

    @GetMapping("/")
    public String route(){
        return "API Working";
    }

    @GetMapping("/broths")
    public Iterable<BrothModel> list(){
        return service.listBroths();
    }
    
}
