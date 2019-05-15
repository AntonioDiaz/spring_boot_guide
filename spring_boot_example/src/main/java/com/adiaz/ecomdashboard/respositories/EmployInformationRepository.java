package com.adiaz.ecomdashboard.respositories;


import com.adiaz.ecomdashboard.entities.EmployInformation;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
@Qualifier(value = "employInformationRepository")
public interface EmployInformationRepository extends JpaRepository<EmployInformation, Long> { }
