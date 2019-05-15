package com.adiaz.ecomdashboard.controllers;

import com.adiaz.ecomdashboard.entities.CompanyRevenue;
import com.adiaz.ecomdashboard.entities.EmployInformation;
import com.adiaz.ecomdashboard.entities.ProductCategory;
import com.adiaz.ecomdashboard.respositories.ProductCategoryRepository;
import com.adiaz.ecomdashboard.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestEndpointController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping("/revenue")
    public List<CompanyRevenue> getCompanyRevenue(){
        return dashboardService.getTodayRevenueDash();
    }

    @GetMapping("/categories/all")
    public List<ProductCategory> getProductCategoriesAll() {
        return dashboardService.getCategories();
    }

    @GetMapping("/categories/best")
    public List<ProductCategory> getProductCategoriesBest(){
        return dashboardService.getBestCategory();
    }

    @GetMapping("/employees")
    public List<EmployInformation> getEmployees() {
        return dashboardService.getEmployees();
    }

    @GetMapping("/employees/{id}")
    public EmployInformation getEmployeeById(@PathVariable Long id) {
        return dashboardService.findEmployInformationById(id);
    }
    @GetMapping("/employees/delete/{id}")
    public String deleteById(@PathVariable Long id) {
        try {
            dashboardService.deleteEmployInformationById(id);
        } catch (Exception e) {
            return "delete ERROR: " + e.getMessage();
        }
        return "delete DONE";
    }

}
