package com.adiaz.ecomdashboard.bootstrap;

import com.adiaz.ecomdashboard.entities.CompanyRevenue;
import com.adiaz.ecomdashboard.entities.EmployInformation;
import com.adiaz.ecomdashboard.entities.ProductCategory;
import com.adiaz.ecomdashboard.respositories.CompanyRevenueRepository;
import com.adiaz.ecomdashboard.respositories.EmployInformationRepository;
import com.adiaz.ecomdashboard.respositories.ProductCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class SpringJpaBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    CompanyRevenueRepository companyRevenueRepository;

    @Autowired
    ProductCategoryRepository productCategoryRepository;

    @Autowired
    EmployInformationRepository employInformationRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        companyRevenueRepository.save(createRevenue("ene", 2_000));
        companyRevenueRepository.save(createRevenue("feb", 5_000));
        companyRevenueRepository.save(createRevenue("mar", 4_000));
        companyRevenueRepository.save(createRevenue("jun", 2_000));
        companyRevenueRepository.save(createRevenue("jul", 15_000));
        companyRevenueRepository.save(createRevenue("aug", 5_000));
        productCategoryRepository.save(createProductCategory("01 product", 12, false));
        productCategoryRepository.save(createProductCategory("02 product BEST CATEGORY", 13, true));
        employInformationRepository.save(createEmployInformation("Miguel de Cervantes"));
        employInformationRepository.save(createEmployInformation("James Joice"));
    }

    private EmployInformation createEmployInformation(String name) {
        EmployInformation employInformation = new EmployInformation();
        employInformation.setName(name);
        return employInformation;
    }

    private CompanyRevenue createRevenue(String s, double i) {
        CompanyRevenue companyRevenue = new CompanyRevenue();
        companyRevenue.setMonth(s);
        companyRevenue.setRevenue(i);
        return companyRevenue;
    }

    private ProductCategory createProductCategory(String categoryName, int percentage, boolean bestCategory) {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName(categoryName);
        productCategory.setPercentage(percentage);
        productCategory.setBestCategory(bestCategory);
        return productCategory;
    }

}
