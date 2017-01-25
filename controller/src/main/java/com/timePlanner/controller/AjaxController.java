package com.timePlanner.controller;

import com.timePlanner.dto.*;
import com.timePlanner.service.*;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Set;

@RestController
public class AjaxController {
    private static final Logger LOGGER = LogManager.getLogger(AjaxController.class);

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private SprintService sprintService;

    @PreAuthorize("hasRole('PM')")
    @RequestMapping(path = "/set-priority", method = RequestMethod.POST)
    public ResponseEntity<?> setPriority(@RequestBody Task task, Principal principal, HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        boolean containsFlag = false;
        if(checkAccessUserToTask(principal.getName(),task.getId())){
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
        if(userService.checkAccessUserToProject(principal.getName(),project.getId())){
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

    @PreAuthorize("hasRole('PM')")
    @RequestMapping(path = "/task-for-sprint-{id}id", method = RequestMethod.GET)
    public  ResponseEntity<?>  createTaskPost(@PathVariable int id, Principal principal, HttpServletRequest request) {
        Sprint sprint = sprintService.getSprintWithDetails(id);
        Set<Task> taskSet = sprint.getTasks();
        return new ResponseEntity<>(taskSet, HttpStatus.OK);
    }

    private boolean checkAccessUserToTask(String email,int taskId){
        boolean containsFlag = false;
        try {
            User user = userService.getUserByEmail(email);
            Set<Task> tasksForUser = taskService.findTaskForPM(user);
            for (Task i : tasksForUser) {
                if (i.getId() == taskId) {
                    containsFlag = true;
                }
            }
        } catch (EmptyResultException e) {
            LOGGER.info("User with email: " + email + " not found", e);
        }
        return containsFlag;
    }

}
