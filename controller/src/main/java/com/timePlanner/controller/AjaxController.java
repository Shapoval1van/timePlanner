package com.timePlanner.controller;

import com.timePlanner.dto.*;
import com.timePlanner.service.EmptyResultException;
import com.timePlanner.service.ProjectService;
import com.timePlanner.service.TaskService;
import com.timePlanner.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@RestController
public class AjaxController {
    private static final Logger LOGGER = LogManager.getLogger(AjaxController.class);

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @PreAuthorize("hasRole('PM')")
    @RequestMapping(path = "/set-priority", method = RequestMethod.POST)
    public ResponseEntity<?> setPriority(@RequestBody Task task, Principal principal, HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        Set<Task> tasksForUser = new TreeSet<>();
        boolean containsFlag = false;
        try {
            User  user = userService.getUserByEmail(principal.getName());
            tasksForUser = taskService.findTaskForPM(user);
        } catch (EmptyResultException e) {
            LOGGER.info("request from address:"+remoteAddr+"\n User with email: " +principal.getName() + " not found",e);
            return new ResponseEntity<>(new Message("user not found", MessageType.ERROR),HttpStatus.BAD_REQUEST);
        }
        for(Task i: tasksForUser){
            if(i.getId()==task.getId()){
               containsFlag = true;
            }
        }
        if(containsFlag){
            if(task.getId() == 0){
                LOGGER.info("request from address:"+remoteAddr+"\n request for update priority with empty id");
                return new ResponseEntity<>(new Message("id must be not empty", MessageType.ERROR),HttpStatus.BAD_REQUEST);
            }else if(task.getPriority() == null){
                LOGGER.info("request from address:"+remoteAddr+"\nrequest for update priority with empty priority");
                return new ResponseEntity<>(new Message("priority must be not empty", MessageType.ERROR),HttpStatus.BAD_REQUEST);
            }
            try{
                taskService.updateTaskPriority(task);
                return new ResponseEntity<String>(HttpStatus.OK);
            }catch (Exception e){
                LOGGER.info("request from address:"+remoteAddr+"\ncant update user",e);
                return new ResponseEntity<>(new Message("Internal server problem", MessageType.ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
            return new ResponseEntity<>(new Message("this user haven't access to this task", MessageType.ERROR),HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasRole('PM')")
    @RequestMapping(path = "/update-project-status", method = RequestMethod.POST)
    public ResponseEntity<?> setStatus(@RequestBody Project project, Principal principal, HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        User user;
        boolean containsFlag = false;
        try {
            user = userService.getUserByEmail(principal.getName());
        } catch (EmptyResultException e) {
            LOGGER.info("request from address:"+remoteAddr+"\n User with email: " +principal.getName() + " not found",e);
            return new ResponseEntity<>(new Message("user not found", MessageType.ERROR),HttpStatus.BAD_REQUEST);
        }
        List<Project> projectList = projectService.getProjectsForProjectManager(user.getId());
        for(Project i: projectList){
            if(i.getId()==project.getId()){
                containsFlag = true;
            }
        }
        if(containsFlag){
            if(project.getId() == 0){
                LOGGER.info("request from address:"+remoteAddr+"\n request for update  project state with empty id");
                return new ResponseEntity<>(new Message("id must be not empty", MessageType.ERROR),HttpStatus.BAD_REQUEST);
            }
            try{
                ProjectStatus projectStatus = project.getProjectStatus();
                if(projectStatus == ProjectStatus.CREATED){
                    projectService.setProjectStarted(project.getId());
                }else if(projectStatus == ProjectStatus.STARTED){
                    projectService.setProjectFinished(project.getId());
                }
                return new ResponseEntity<String>(HttpStatus.OK);
            }catch (Exception e){
                LOGGER.info("request from address:"+remoteAddr+"\n cant update project",e);
                return new ResponseEntity<>(new Message("Internal server problem", MessageType.ERROR),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }else {
            return new ResponseEntity<>(new Message("this user haven't access to this project", MessageType.ERROR),HttpStatus.FORBIDDEN);
        }
    }
}
