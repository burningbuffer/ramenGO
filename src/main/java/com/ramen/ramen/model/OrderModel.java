package com.ramen.ramen.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderModel {
    
    private Long id;
    private String description;
    private String image; 
}
