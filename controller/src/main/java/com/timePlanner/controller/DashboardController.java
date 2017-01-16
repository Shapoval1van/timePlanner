package com.timePlanner.controller;

import com.timePlanner.dto.*;
import com.timePlanner.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class DashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SprintService sprintService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping("/dashboard-adm")
    public String dashboardAdmin(Model model){
        Set<Project> finishProjects = null;
        User user = userService.getUserWithDetailsById(1);
        Company company =  companyService.getCompanyWithDetails(user.getCompany().getId());
        List<User> currentWorkers = userService.getAllUsersForCompany(company.getId());
        List<Customer> currentCustomer = customerService.getCustomersWithDetailsByCompanyId(company.getId());
        model.addAttribute("userRole", user.getRole());
        model.addAttribute("user", user);
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
        //TODO add tree set
        return "/dashboard/admin";
    }

    @PreAuthorize("hasRole('PM')")
    @RequestMapping("/dashboard-pm")
    public String dashboardProjectManager(Model model){
        User user = userService.getUserWithDetailsById(3);
        Set<Task> tasks = Collections.synchronizedSet(new TreeSet<>(Comparator.comparing(Task::getId)));
        Project project = projectService.getProjectsForProjectManager(user.getId()).get(0);
        List<Sprint> sprints = sprintService.getSprintsForProject(project.getId()) ;// sorted quickly
        List<User> currentEmployees = userService.getEmployeesForProject(project.getId());
        sprints.parallelStream().filter(s->s.getTasks()!= null).forEach(s -> tasks.addAll(s.getTasks()));
        Collections.sort(sprints, Comparator.comparing(Sprint::getId));
        Collections.sort(currentEmployees, Comparator.comparing(User::getId));
        //for default sent for user pages wish first project in list
        model.addAttribute("sprintCount",sprints.size());
        model.addAttribute("taskCount", tasks.size());
        model.addAttribute("employeeCount", currentEmployees.size());
        model.addAttribute("finishTaskCount",tasks.stream().filter(Task::isFinished).collect(Collectors.toList()).size());
        model.addAttribute("project", project);
        model.addAttribute("userRole", user.getRole());
        model.addAttribute("user", user);
        model.addAttribute("sprints",sprints);
        model.addAttribute("tasks", tasks);
        model.addAttribute("currentEmployees", currentEmployees);

        return "/dashboard/projectManager";
    }
}
