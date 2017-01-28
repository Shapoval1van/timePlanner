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
    public ResponseEntity<?> setPriority(@RequestBody Task task, Principal principal, HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        int projectId = projectService.getProjectForTask(task.getId()).getId();
        if (userService.checkAccessUserToProject(principal.getName(), projectId) == 0) {
            return new ResponseEntity<>(new Message("this user haven't access to this task", MessageType.ERROR), HttpStatus.FORBIDDEN);
        }
        if (task.getId() == 0) {
            LOGGER.info("request from address:" + remoteAddr + "\n request for update priority with empty id");
            return new ResponseEntity<>(new Message("id must be not empty", MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }if (task.getPriority() == null) {
            LOGGER.info("request from address:" + remoteAddr + "\nrequest for update priority with empty priority");
            return new ResponseEntity<>(new Message("priority must be not empty", MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }
        try {
            taskService.updateTaskPriority(task);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.info("request from address:" + remoteAddr + "\ncant update priority", e);
            return new ResponseEntity<>(new Message("Internal server problem", MessageType.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('PM')")
    @RequestMapping(path = "/update-project-status", method = RequestMethod.POST)
    public ResponseEntity<?> setStatus(@RequestBody Project project, Principal principal, HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        if (userService.checkAccessUserToProject(principal.getName(), project.getId()) != 0) {
            if (project.getId() == 0) {
                LOGGER.info("request from address:" + remoteAddr + "\n request for update  project state with empty id");
                return new ResponseEntity<>(new Message("id must be not empty", MessageType.ERROR), HttpStatus.BAD_REQUEST);
            }
            try {
                Status projectStatus = project.getProjectStatus();
                if (projectStatus == Status.CREATED) {
                    projectService.setProjectStarted(project.getId());
                } else if (projectStatus == Status.STARTED) {
                    projectService.setProjectFinished(project.getId());
                }
                return new ResponseEntity<String>(HttpStatus.OK);
            } catch (Exception e) {
                LOGGER.info("request from address:" + remoteAddr + "\n cant update project", e);
                return new ResponseEntity<>(new Message("Internal server problem", MessageType.ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else {
            return new ResponseEntity<>(new Message("this user haven't access to this project", MessageType.ERROR), HttpStatus.FORBIDDEN);
        }
    }

    @PreAuthorize("hasRole('PM')")
    @RequestMapping(path = "/task-for-sprint-{id}id", method = RequestMethod.GET)
    public ResponseEntity<?> createTaskPost(@PathVariable int id, Principal principal, HttpServletRequest request) {
        Sprint sprint = sprintService.getSprintWithDetails(id);
        Set<Task> taskSet = sprint.getTasks();
        if(taskSet==null){
            return new ResponseEntity<>(new Message("empty result", MessageType.INFO),HttpStatus.OK);
        }
        return new ResponseEntity<>(taskSet, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('PM')")
    @RequestMapping(path = "/assign-tasks", method = RequestMethod.POST)
    public ResponseEntity<?> assignTaskToUser(@RequestBody Task task, Principal principal, HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        int projectId = projectService.getProjectForTask(task.getId()).getId();
        if (userService.checkAccessUserToProject(principal.getName(), projectId) == 0){
            LOGGER.info("request from address:" + remoteAddr + "\n cant update user");
            return new ResponseEntity<>(new Message("this user haven't access to this task", MessageType.ERROR), HttpStatus.FORBIDDEN);
        }
        if (task.getId() == 0) {
            LOGGER.info("request from address:" + remoteAddr + "\n request for update user with empty  task id");
            return new ResponseEntity<>(new Message("id must be not empty", MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }
        if(task.getUsers()==null && task.getUsers().size()==0){
            LOGGER.info("request from address:" + remoteAddr + "\n request for update user with empty  users");
            return new ResponseEntity<>(new Message("users must be not empty", MessageType.ERROR), HttpStatus.BAD_REQUEST);
        }
        if(taskService.updateResponsibleUsers(task)){
            return new ResponseEntity<>(new Message("Responsible updated", MessageType.SUCCESS),HttpStatus.OK);
        }
        return new ResponseEntity<>(new Message("Users must be not empty", MessageType.ERROR), HttpStatus.BAD_REQUEST);
    }

    @PreAuthorize("hasRole('EMPLOYEE')")
    @RequestMapping(path = "/update-task-status", method = RequestMethod.POST)
    public ResponseEntity<?> updateTaskStatus(@RequestBody Task task, Principal principal, HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        Status uptatedStatus;
        User user = userService.getUserWithDetailsByEmail(principal.getName());
        Task taskFromDB = user.getTasks().stream().filter(t->t.getId()==task.getId()).findFirst().orElse(null);
        Status status = taskFromDB.getTaskStatus();
        if(task.getId()==0){
            LOGGER.info("request from user with address: " + remoteAddr + " was rejected \n task must have id");
            return new ResponseEntity<>(new Message("Task is not valid", MessageType.ERROR),HttpStatus.BAD_REQUEST);
        }
        if(taskFromDB == null){
            LOGGER.info("request from user with address: " + remoteAddr + " was rejected \n user "+ principal.getName()
            +"dose not have access to this task");
            return new ResponseEntity<>(new Message("Access denied", MessageType.ERROR),HttpStatus.FORBIDDEN);
        }
        if(status == Status.CREATED){
            uptatedStatus = Status.STARTED;
            taskService.setTaskStarted(task.getId());
        }else if(status == Status.STARTED){
            uptatedStatus = Status.FINISHED;
            Task taskWithDetails = taskService.getTaskWithDetailsById(task.getId());
            Set<Task> tasksDependentSet = taskWithDetails.getTasks();
            if(tasksDependentSet!=null){
                Task taskDependent = tasksDependentSet.stream().filter(t->!t.isFinished()).findAny().orElse(null);
                if(taskDependent!=null){
                    return new ResponseEntity<>(new Message("finish dependent task", MessageType.ERROR),HttpStatus.BAD_REQUEST);
                }
            }
            taskService.setTaskFinished(task.getId());
        }else{
            uptatedStatus = Status.FINISHED;
        }
        return new ResponseEntity<>(new Message(uptatedStatus.toString(), MessageType.SUCCESS),HttpStatus.OK);
    }
}
