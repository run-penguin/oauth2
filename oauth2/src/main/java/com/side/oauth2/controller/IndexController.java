package com.side.oauth2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {


    @GetMapping("")
    public String indexPage() {
        return "index.html";
    }

    @GetMapping("login")
    public String loginPage() {
        return "login.html";
    }
    
}
