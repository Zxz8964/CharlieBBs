package com.itvictorkile.bbsforexample.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class ExampleController {
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("message", "Hello, Welcome to your Spring Boot application!");
        return "home";
    }
}
