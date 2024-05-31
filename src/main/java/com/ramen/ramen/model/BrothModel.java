package com.ramen.ramen.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class BrothModel {
    
    @Id
    private Long id;
    private String imageInactive;
    private String imageActive;
    private String name;
    private String description;
    private float price;



}
