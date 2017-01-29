package com.timePlanner.controller;

import com.timePlanner.controller.validator.UserFormValidator;
import com.timePlanner.dto.Role;
import com.timePlanner.dto.User;
import com.timePlanner.dto.UserForm;
import com.timePlanner.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class SecurityController {
    //all var thread safe
    private static final Logger LOGGER = LogManager.getLogger(SecurityController.class);
    private String remoteAddr;

    @Autowired
    @Qualifier(value = "adminFormValidator")
    public UserFormValidator adminFormValidator;

    @Autowired
    public UserDetailsService userDetailsService;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    public UserService userService;

    @RequestMapping("/login")
    public String login() {
        return "/security/login";
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping("/success")
    public String afterLoginSuccess(Authentication authentication) {
        String authority = authentication.getAuthorities().iterator().next().getAuthority();
        switch (authority) {
            case "ADMIN":
                return "redirect:/dashboard-adm";
            case "PM":
                return "redirect:/dashboard-pm";
            case "EMPLOYEE":
                return "redirect:/dashboard-emp";
            case "CUSTOMER":
                return "redirect:/dashboard-cust";
            default:
                return "redirect:/";
        }
    }

    @RequestMapping(path = "/registration", method = RequestMethod.GET)
    public String registrationGetPage(ModelMap map) {
        map.addAttribute("adminForm", new UserForm());
        return "/security/registration";
    }

    @RequestMapping(path = "/registration", method = RequestMethod.POST)
    public String registrationPost(@ModelAttribute("adminForm") UserForm userForm, BindingResult result, HttpServletRequest request) {
        remoteAddr = request.getRemoteAddr();
        adminFormValidator.validate(userForm, result);
        if (result.hasErrors()) {
            return "/security/registration";
        }
        User user = userForm;
        user.setRole(Role.ADMIN);
        userService.createUserAdmin(user);
        autologin(user.getEmail(), user.getPassword());
        return "redirect:/success";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    private void autologin(String username, String password){
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
        authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            LOGGER.info("user: " +userDetails.getUsername()+"\n address:"+remoteAddr+"\n was success registration");
        }
    }
}
