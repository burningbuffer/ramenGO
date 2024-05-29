package com.ramen.ramen.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ramen.ramen.model.OrderModel;

@Repository
public interface OrderRepository extends CrudRepository<OrderModel, Long>{
        
}
