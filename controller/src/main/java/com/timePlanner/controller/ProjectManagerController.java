package com.timePlanner.controller;

import com.timePlanner.dto.Project;
import com.timePlanner.dto.Role;
import com.timePlanner.dto.Sprint;
import com.timePlanner.dto.User;
import com.timePlanner.service.EmptyResultException;
import com.timePlanner.service.ProjectService;
import com.timePlanner.service.SprintService;
import com.timePlanner.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Controller
public class ProjectManagerController {
    private static final Logger LOGGER = LogManager.getLogger(ProjectManagerController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SprintService sprintService;

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('PM')")
    @RequestMapping(path = "/create-sprint/for-{id}id", method = RequestMethod.GET)
    public String createSprint(ModelMap model, Principal principal, @PathVariable("id") int id, HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        Sprint previousSprint;
        User user;
        try {
            user = userService.getUserByEmail(principal.getName());

        } catch (EmptyResultException e) {
            LOGGER.info("request from address:" + remoteAddr + "\n User with email: " + principal.getName() + " not found", e);
            return "redirect:/dashboard-pm";
        }

        List<Project> projectList = projectService.getProjectsForProjectManager(user.getId());
        if (projectList.size() == 0) {
            return "/dashboard/projectNotFound";
        }
        Project project = projectList.stream().filter(project1 -> project1.getId() == id).findFirst().orElse(null);
        if (project == null) {
            //if project with id not found that mean user don't have  access to this project
            return "redirect:/dashboard-pm";
        }
        List<Sprint> sprintList = sprintService.getSprintsForProjectWithDetails(project.getId());
        if(sprintList.size()==0){
            previousSprint = null;
        }else {
            previousSprint = sprintList.get(sprintList.size()-1);
        }
        model.addAttribute("userRole", Role.PM);
        model.addAttribute("currentProjectId", id);
        model.addAttribute("sprintForm", new Sprint());
        model.addAttribute("previousSpring", previousSprint);
        return "/pm/createSprint";
    }


    @PreAuthorize("hasRole('PM')")
    @RequestMapping(path = "/create-sprint", method = RequestMethod.POST)
    public String createSprintPost(@ModelAttribute("sprintForm") Sprint sprint, ModelMap model, Principal principal, HttpServletRequest request) {
        sprintService.saveSprint(sprint);
        return "redirect:/dashboard-pm?id="+sprint.getProject().getId();
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}
