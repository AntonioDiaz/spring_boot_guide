package com.adiaz.ecomdashboard.controllers;

import com.adiaz.ecomdashboard.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ThymeleafController {

    @Autowired
    DashboardService dashboardService;

    @GetMapping({"/dashboard"})
    public String goDashboard(Model model) {
        model.addAttribute("cr", dashboardService.getTodayRevenueDashMap());
        model.addAttribute("employees", dashboardService.getEmployees());
        return "dashboard";
    }



}
