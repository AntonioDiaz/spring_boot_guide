package com.adiaz.ecomdashboard.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

    @GetMapping({"", "/"})
    public String index() {
        return "index";
    }

    @GetMapping({"/login"})
    public String login() {
        return "index";
    }

    @GetMapping({"/access-denied"})
    public String accessDenied() {
        return "/access-denied";
    }

}
