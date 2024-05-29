package com.ramen.ramen.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ramen.ramen.model.BrothModel;

@Repository
public interface BrothRepository extends CrudRepository<BrothModel, Long>{
    
}
