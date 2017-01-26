package com.timePlanner.controller;

import com.timePlanner.dto.*;
import com.timePlanner.service.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class ProjectManagerController {
    private static final Logger LOGGER = LogManager.getLogger(ProjectManagerController.class);

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SprintService sprintService;

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @PreAuthorize("hasRole('PM')")
    @RequestMapping(path = "/create-sprint/for-{id}id", method = RequestMethod.GET)
    public String createSprint(ModelMap model, Principal principal, @PathVariable("id") int id, HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        Sprint previousSprint;
        User user;
        if(!userService.checkAccessUserToProject(principal.getName(),id)){
            return "redirect:/dashboard-pm";
        }
        List<Sprint> sprintList = sprintService.getSprintsForProjectWithDetails(id);
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


    @PreAuthorize("hasRole('PM')")
    @RequestMapping(path = "/create-task/for-{id}id", method = RequestMethod.GET)
    public String createTask(ModelMap model, Principal principal, @PathVariable("id") int id, HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        User user;
        if(!userService.checkAccessUserToProject(principal.getName(),id)){
            return "redirect:/dashboard-pm";
        }
        List<Sprint> sprintList = sprintService.getSprintsForProjectWithDetails(id);
        Set<Task> taskList = (sprintList!=null & sprintList.size()!=0)?sprintList.get(0).getTasks():null;
        model.addAttribute("taskList",taskList);
        model.addAttribute("sprintList",sprintList);
        model.addAttribute("taskForm", new Task());

        //mandatory for correct creation navigation bar
        model.addAttribute("userRole", Role.PM);
        model.addAttribute("currentProjectId", id);
        return "/pm/createTask";
    }

    @PreAuthorize("hasRole('PM')")
    @RequestMapping(path = "/create-task", method = RequestMethod.POST)
    public String createTaskPost(@ModelAttribute("taskForm") Task task, ModelMap model, Principal principal, HttpServletRequest request) {
        if(task.getSprint()==null){
            return "redirect:/dashboard-pm";
        }else {
            Sprint sprint = sprintService.getSprintWithDetails(task.getSprint().getId());
            taskService.saveTask(task);
            return "redirect:/dashboard-pm?id="+sprint.getProject().getId();
        }
    }

    @PreAuthorize("hasRole('PM')")
    @RequestMapping(path = "/assign-tasks/for-{id}id", method = RequestMethod.GET)
    public String assignTaskToUser(ModelMap model, @PathVariable("id") int id, Principal principal,HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        //User user = projectService

        //mandatory for correct creation navigation bar
        model.addAttribute("currentProjectId", id);
        model.addAttribute("userRole", Role.PM);
        return "/pm/assignTasks";
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        webDataBinder.registerCustomEditor(HashSet.class, "tasks", new CustomCollectionEditor(HashSet.class)
        {
            @Override
            protected Object convertElement(Object element)
            {
                Integer id = null;
                if(element instanceof String && !"".equals((String)element)){
                    try{
                        id = Integer.parseInt((String)element);
                    }
                    catch (NumberFormatException e) {
                        LOGGER.info("Element was " + ((String) element));
                        e.printStackTrace();
                    }
                }
                Task task = new Task();
                task.setId(id);
                return task;
            }
        });
    }
}
