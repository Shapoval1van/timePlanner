package com.timePlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/403")
    public String accessDenied(){
        return "403";
    }

    @RequestMapping("/contacts")
    public String contacts(){
        return "contacts";
    }
}
