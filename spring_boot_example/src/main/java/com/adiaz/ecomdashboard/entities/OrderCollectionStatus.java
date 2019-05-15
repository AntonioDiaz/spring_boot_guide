package com.adiaz.ecomdashboard.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="ORDER_COLLECTION_STATUS")
@Data
public class OrderCollectionStatus extends KeyEntity {
    private int newOrders;
    private double revenue;
    private int shipped;
    private int returned;
}
