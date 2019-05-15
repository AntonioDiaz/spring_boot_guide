package com.adiaz.ecomdashboard.services;

import com.adiaz.ecomdashboard.entities.CompanyRevenue;
import com.adiaz.ecomdashboard.entities.EmployInformation;
import com.adiaz.ecomdashboard.entities.ProductCategory;

import java.util.List;
import java.util.Map;

public interface DashboardService {
    List<CompanyRevenue> getTodayRevenueDash();
    Map<String, Object> getTodayRevenueDashMap();
    List<ProductCategory> getBestCategory();
    List<ProductCategory> getCategories();
    List<EmployInformation> getEmployees();
    void addEmployInformation(EmployInformation employInformation);
    EmployInformation updateEmployInformation(EmployInformation employInformation);
    void deleteEmployInformationById(Long id);
    EmployInformation findEmployInformationById(Long id);
}
