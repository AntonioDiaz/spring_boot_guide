package com.adiaz.ecomdashboard;

import com.adiaz.ecomdashboard.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.adiaz.ecomdashboard.respositories")
public class EcomdashboardApplication {

	@Autowired
	DashboardService dashboardService;


	public static void main(String[] args) {
		SpringApplication.run(EcomdashboardApplication.class, args);



	}
}
