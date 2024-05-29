package com.ramen.ramen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ramen.ramen.model.AnswerModel;
import com.ramen.ramen.model.ProteinModel;
import com.ramen.ramen.repository.ProteinRerpository;

@Service
public class ProteinService {
    
    @Autowired
    private ProteinRerpository repository;

    @Autowired
    private AnswerModel answerModel;

    public Iterable<ProteinModel> listProteins(){
        return repository.findAll();
    }
}
