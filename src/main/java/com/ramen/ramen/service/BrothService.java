package com.ramen.ramen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramen.ramen.model.AnswerModel;
import com.ramen.ramen.model.BrothModel;
import com.ramen.ramen.repository.BrothRepository;

@Service
public class BrothService {
    
    @Autowired
    private BrothRepository repository;

    @Autowired
    private AnswerModel answerModel;

    public Iterable<BrothModel> listBroths() {
        return repository.findAll();
    }

}
