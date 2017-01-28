package com.timePlanner.controller;

import com.timePlanner.dto.Status;
import com.timePlanner.dto.Task;
import com.timePlanner.dto.User;
import com.timePlanner.service.TaskService;
import com.timePlanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
public class EmployeeController {

    @Autowired
    private UserService userService;

    @Autowired
    private TaskService taskService;

    @PreAuthorize("hasRole('EMPLOYEE')")
    @RequestMapping("/show-task/{id}id")
    public String showTask(Principal principal, @PathVariable("id") int id, Model model){
        Task task = taskService.getTaskWithDetailsById(id);
        if(task == null){
            return "redirect:/dashboard-emp";
        }
        User user = task.getUsers().stream().filter(u->u.getEmail().equals(principal.getName())).findFirst().orElse(null);
        if(user == null){
            return "redirect:/dashboard-emp";
        }
        long  dayDiff = (task.getPlanFinishDate().getTime()-System.currentTimeMillis())/24;
        if(task.getTaskStatus()!= Status.FINISHED && (dayDiff<2 && dayDiff>=0)){
            model.addAttribute("warningFlag", true);
        }
        if(task.getTaskStatus()!= Status.FINISHED && (dayDiff<0)){
            model.addAttribute("errorFlag", true);
        }
        model.addAttribute("task", task);
        return "/employee/task";
    }
}
