package com.adiaz.ecomdashboard.entities;


import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="COMPANY_REVENUE")
@Data
public class CompanyRevenue extends KeyEntity {
    @Column(name="REVENUE_MONTH", nullable=false)
    private String month;
    @Column(name="REVENUE", nullable=false)
    private double revenue;
    @Column(name="EXPENSE")
    private double expense;
    @Column(name="MARGINS")
    private double margins;
}
