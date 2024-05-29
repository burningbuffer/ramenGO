package com.ramen.ramen.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "order")
@Getter
@Setter
public class OrderModel {
    private long brothId;
    private long proteinId;
}
