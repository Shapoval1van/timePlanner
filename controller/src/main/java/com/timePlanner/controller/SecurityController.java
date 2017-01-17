package com.timePlanner.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SecurityController {

   @RequestMapping("/login")
   public String login(){
       return "/security/login";
   }

   @PreAuthorize("isAuthenticated()")
   @RequestMapping("success")
   public String afterLoginSuccess(Authentication authentication){
       String authority = authentication.getAuthorities().iterator().next().getAuthority();
       switch (authority){
           case "ADMIN":
               return "redirect:/dashboard-adm";
           case "PM":
               return "redirect:/dashboard-pm";
           default:
               return "redirect:/";
            // TODO add redirect for another
       }
   }
}
