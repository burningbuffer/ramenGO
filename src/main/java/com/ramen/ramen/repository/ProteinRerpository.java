package com.ramen.ramen.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ramen.ramen.model.ProteinModel;

@Repository
public interface ProteinRerpository extends CrudRepository<ProteinModel, Long>{
    
}
