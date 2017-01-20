package com.timePlanner.controller;

import com.timePlanner.dto.Project;
import com.timePlanner.dto.Role;
import com.timePlanner.dto.User;
import com.timePlanner.service.ProjectService;
import com.timePlanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/create-project", method = RequestMethod.GET)
    public String createProject(ModelMap model, Principal principal){
        User user = userService.getUserWithDetailsByEmail(principal.getName());
        List<User> currentPM = userService.getAllUsersForCompany(user.getCompany().getId())
                .stream()
                .filter(u-> (u.getRole()!= Role.ADMIN)&&(u.getRole()!= Role.EMPLOYEE)&&(u.getRole()!= Role.CUSTOMER)).collect(Collectors.toList());
        model.addAttribute("userRole", Role.ADMIN);
        model.addAttribute("projectForm", new Project());
        model.addAttribute("currentPM", currentPM);
        return "/admin/createProject";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/create-project", method = RequestMethod.POST)
    public String createProjectPOST(@ModelAttribute("projectForm") Project project, Principal principal){
        User user = userService.getUserWithDetailsByEmail(principal.getName());
        project.setCompany(user.getCompany());
        projectService.saveProject(project);
        return "redirect:/dashboard-adm";
    }


    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }
}

