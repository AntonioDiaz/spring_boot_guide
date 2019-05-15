package com.adiaz.ecomdashboard.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="PRODUCT_CATEGORY")
@Data
public class ProductCategory extends KeyEntity {
    private String categoryName;
    private int percentage;
    private boolean bestCategory;

}
