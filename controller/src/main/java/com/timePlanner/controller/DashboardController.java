package com.timePlanner.controller;

import com.timePlanner.dto.Company;
import com.timePlanner.dto.Customer;
import com.timePlanner.dto.Project;
import com.timePlanner.dto.User;
import com.timePlanner.service.CompanyService;
import com.timePlanner.service.CustomerService;
import com.timePlanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CustomerService customerService;

    @RequestMapping("/dashboard-adm")
    public String dashboard(Model model){
        Set<Project> finishProjects = null;
        User user = userService.getUserWithDetailsById(1);
        Company company =  companyService.getCompanyWithDetails(user.getCompany().getId());
        List<User> currentWorkers = userService.getAllUsersForCompany(company.getId());
        List<Customer> currentCustomer = customerService.getCustomersWithDetailsByCompanyId(company.getId());
        model.addAttribute("userName",user.getFirstName()+" "+user.getLastName());
        model.addAttribute("company", user.getCompany());
        model.addAttribute("projectCount",company.getProjects()!=null ? company.getProjects().size():0);
        if(company.getProjects()!=null){
            finishProjects = company.getProjects().stream().filter(Project::isFinished).collect(Collectors.toSet());
        }
        model.addAttribute("currentWorkersCount",currentWorkers!=null ? currentWorkers.size():0);
        model.addAttribute("currentCustomersCount",currentCustomer!=null ? currentCustomer.size():0);
        model.addAttribute("finishProjectsCount",finishProjects!=null ? finishProjects.size():0);
        model.addAttribute("projects", company.getProjects());
        model.addAttribute("currentWorkers",currentWorkers);
        model.addAttribute("currentCustomers", currentCustomer);
        return "adminDashboard";
    }
}
