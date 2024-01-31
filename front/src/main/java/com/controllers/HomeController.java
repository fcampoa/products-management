package com.controllers;

import com.clients.RabbitMQClient;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class HomeController {
    public HomeController() {
    }
    @GetMapping()
    public String home(Model model) {
        model.addAttribute("message", "message sent!");
        return "home";
    }
}
