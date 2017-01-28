package com.timePlanner.controller;

import com.timePlanner.dto.*;
import com.timePlanner.service.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class DashboardController {
    private static final Logger LOGGER = LogManager.getLogger(DashboardController.class);
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
    public String dashboardAdmin(Model model, Principal principal) {
        Set<Project> finishProjects = null;
        User user = userService.getUserWithDetailsByEmail(principal.getName());
        Company company = companyService.getCompanyWithDetails(user.getCompany().getId());
        List<User> currentWorkers = userService.getAllUsersForCompany(company.getId());
        List<Customer> currentCustomer = customerService.getCustomersWithDetailsByCompanyId(company.getId());
        model.addAttribute("userRole", user.getRole());
        model.addAttribute("user", user);
        model.addAttribute("projectCount", company.getProjects() != null ? company.getProjects().size() : 0);
        if (company.getProjects() != null) {
            finishProjects = company.getProjects().stream().filter(Project::isFinished).collect(Collectors.toSet());
        }
        model.addAttribute("currentWorkersCount", currentWorkers != null ? currentWorkers.size() : 0);
        model.addAttribute("currentCustomersCount", currentCustomer != null ? currentCustomer.size() : 0);
        model.addAttribute("finishProjectsCount", finishProjects != null ? finishProjects.size() : 0);
        model.addAttribute("company", company);
        model.addAttribute("projects", company.getProjects());
        model.addAttribute("currentWorkers", currentWorkers);
        model.addAttribute("currentCustomers", currentCustomer);
        return "/dashboard/admin";
    }

    @PreAuthorize("hasRole('PM')")
    @RequestMapping("/dashboard-pm")
    public String dashboardProjectManager(Model model, Principal principal, @RequestParam(value = "id", required = false) Integer id) {
        User user = userService.getUserWithDetailsByEmail(principal.getName());
        List<Project> projectList = projectService.getProjectsForProjectManager(user.getId());
        Set<Task> tasks = Collections.synchronizedSet(new TreeSet<>(Comparator.comparing(Task::getId)));
        if (projectList.size() == 0) {
            return "/dashboard/projectNotFound";
        }
        Project project;
        if (id == null) {
            //for default sent for user pages wish first project in list
            project = projectList.get(0);
        } else {
            project = projectList.stream().filter(project1 -> project1.getId() == id).findFirst().orElse(null);
            if (project == null) {
                //if project with id not found that mean user dont have  access to this project
                return "redirect:/dashboard-pm";
            }
        }
        List<Sprint> sprints = sprintService.getSprintsForProjectWithDetails(project.getId());// sorted quickly
        List<User> currentEmployees = userService.getEmployeesForProject(project.getId());
        sprints.parallelStream().filter(s -> s.getTasks() != null).forEach(s -> tasks.addAll(s.getTasks()));
        Collections.sort(sprints, Comparator.comparing(Sprint::getId));
        Collections.sort(currentEmployees, Comparator.comparing(User::getId));

        //mandatory for correct creation navigation bar
        model.addAttribute("userRole", user.getRole());
        model.addAttribute("currentProjectId", project.getId());

        model.addAttribute("projectList", projectList);
        model.addAttribute("projectStatus", project.getProjectStatus());
        model.addAttribute("projectFinished", project.isFinished());
        model.addAttribute("projectStarted", project.isStarted());
        model.addAttribute("sprintCount", sprints.size());
        model.addAttribute("taskCount", tasks.size());
        model.addAttribute("employeeCount", currentEmployees.size());
        model.addAttribute("finishTaskCount", tasks.stream().filter(Task::isFinished).collect(Collectors.toList()).size());
        model.addAttribute("project", project);
        model.addAttribute("user", user);
        model.addAttribute("sprints", sprints);
        model.addAttribute("tasks", tasks);
        model.addAttribute("currentEmployees", currentEmployees);
        return "/dashboard/projectManager";
    }


    @PreAuthorize("hasRole('EMPLOYEE')")
    @RequestMapping("/dashboard-emp")
    public String dashboardEmployee(Model model, Principal principal){
        User user = userService.getUserWithDetailsByEmail(principal.getName());
        model.addAttribute("user", user);
        Set<Task> taskSet =  user.getTasks();
        if(taskSet==null || taskSet.size()==0){
            model.addAttribute("company", user.getCompany());
            model.addAttribute("tasksNewCount",0);
            model.addAttribute("tasksInWorksCount", 0);
            model.addAttribute("tasksFinishedCount",0);
            return "/dashboard/employee";
        }
        Set<Task> tasksNew = taskSet.stream().filter(t->t.getTaskStatus()==Status.CREATED).collect(Collectors.toSet());
        Set<Task> tasksInWork = taskSet.stream().filter(t->t.getTaskStatus()==Status.STARTED).collect(Collectors.toSet());
        Set<Task> tasksFinished = taskSet.stream().filter(t->t.getTaskStatus()==Status.FINISHED).collect(Collectors.toSet());

        model.addAttribute("company", user.getCompany());
        model.addAttribute("tasksNewCount", tasksNew.size());
        model.addAttribute("tasksInWorksCount", tasksInWork.size());
        model.addAttribute("tasksFinishedCount", tasksFinished.size());
        model.addAttribute("tasksNew",tasksNew);
        model.addAttribute("tasksInWork",tasksInWork);
        model.addAttribute("tasksFinished",tasksFinished);
        return "/dashboard/employee";
    }


}
