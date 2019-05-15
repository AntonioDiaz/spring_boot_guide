package com.adiaz.ecomdashboard.services;

import com.adiaz.ecomdashboard.entities.CompanyRevenue;
import com.adiaz.ecomdashboard.entities.EmployInformation;
import com.adiaz.ecomdashboard.entities.ProductCategory;
import com.adiaz.ecomdashboard.respositories.CompanyRevenueRepository;
import com.adiaz.ecomdashboard.respositories.EmployInformationRepository;
import com.adiaz.ecomdashboard.respositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired
    private CompanyRevenueRepository companyRevenueRepository;

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private EmployInformationRepository employInformationRepository;

    @Override
    public List<CompanyRevenue> getTodayRevenueDash() {
        return companyRevenueRepository.findAll();
    }

    @Override
    public Map<String, Object> getTodayRevenueDashMap() {
        Map<String, Object> companyRevenueMap = new HashMap<>();
        List<CompanyRevenue> all = companyRevenueRepository.findAll();
        List<String> label = new ArrayList<>();
        List<String> revenue = new ArrayList<>();
        all.forEach(companyRevenue -> {
            label.add(companyRevenue.getMonth());
            revenue.add(Double.toString(companyRevenue.getRevenue()));
        });
        companyRevenueMap.put("crLabels", label.toString());
        companyRevenueMap.put("crRevenue", revenue.toString());
        companyRevenueMap.put("totalExpense", "totalExpense value");
        companyRevenueMap.put("totalMargin", "totalMargin value");
        companyRevenueMap.put("totalRevenue", "totalRevenue value");
        return companyRevenueMap;
    }

    @Override
    public List<ProductCategory> getBestCategory() {
        return productCategoryRepository.findByBestCategory(true);
    }

    @Override
    public List<ProductCategory> getCategories() {
        return productCategoryRepository.findAll();
    }

    public List<EmployInformation> getEmployees() {
        return employInformationRepository.findAll();
    }

    @Override
    public void addEmployInformation(EmployInformation employInformation) {
        employInformationRepository.save(employInformation);
    }

    @Override
    public EmployInformation updateEmployInformation(EmployInformation employInformation) {
        return employInformationRepository.save(employInformation);
    }

    @Override
    public void deleteEmployInformationById(Long id) {
        employInformationRepository.deleteById(id);
    }
    @Override
    public EmployInformation findEmployInformationById(Long id) {
        return employInformationRepository.findById(id).orElse(null);
    }

}
