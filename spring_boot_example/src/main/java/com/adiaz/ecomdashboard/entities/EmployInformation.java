package com.adiaz.ecomdashboard.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name="EMPLOYEE_INFORMATION")
@Data
public class EmployInformation extends KeyEntity {
    private String name;
    private String position;
    private String officeLocation;
    private int age;
    private Date startDate;
    private double salary;

}
