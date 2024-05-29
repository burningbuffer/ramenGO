package com.ramen.ramen.control;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ramen.ramen.model.ProteinModel;
import com.ramen.ramen.service.ProteinService;

@RestController
@CrossOrigin(origins = "*")
public class ProteinControl {
    
    @Autowired
    private ProteinService service;

    @GetMapping("/proteins")
    public Iterable<ProteinModel> list(){
        return service.listProteins();
    }

}
