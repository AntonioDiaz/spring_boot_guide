package com.adiaz.ecomdashboard.respositories;

import com.adiaz.ecomdashboard.entities.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
    List<ProductCategory> findByBestCategory(boolean bestCategory);
}
