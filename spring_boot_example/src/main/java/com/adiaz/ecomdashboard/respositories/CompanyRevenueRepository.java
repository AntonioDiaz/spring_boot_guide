package com.adiaz.ecomdashboard.respositories;

import com.adiaz.ecomdashboard.entities.CompanyRevenue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier(value = "companyRevenueRepository")
public interface CompanyRevenueRepository extends JpaRepository <CompanyRevenue, Long> {
}
