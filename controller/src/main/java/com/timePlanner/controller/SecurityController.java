package com.timePlanner.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {

   @RequestMapping("/login")
   public String login(){
       return "/security/login";
   }

//   @PreAuthorize("isAuthenticated()")
//   @RequestMapping("success")
//   public String afterLoginSuccess(){
//       return "/security/login";
//   }
}
