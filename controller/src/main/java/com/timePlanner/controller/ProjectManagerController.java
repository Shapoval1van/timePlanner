package com.timePlanner.controller;

import com.timePlanner.dto.Role;
import com.timePlanner.dto.Sprint;
import com.timePlanner.dto.Task;
import com.timePlanner.dto.User;
import com.timePlanner.service.ProjectService;
import com.timePlanner.service.SprintService;
import com.timePlanner.service.TaskService;
import com.timePlanner.service.UserService;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomCollectionEditor;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLConnection;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


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
        if(userService.checkAccessUserToProject(principal.getName(),id)==0){
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
        if(userService.checkAccessUserToProject(principal.getName(),id)==0){
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
        int userId = userService.checkAccessUserToProject(principal.getName(),id);
        if(userId==0){
            return "redirect:/dashboard-pm";
        }
        Set<Task> taskWithDetailsSet =  new TreeSet<>(Comparator.comparing(Task::getId));
        for (Task task: taskService.findTaskForProject(id)){
            taskWithDetailsSet.add(taskService.getTaskWithDetailsById(task.getId()));
        }
        taskWithDetailsSet = taskWithDetailsSet.stream().filter(t->!(t.getUsers()!=null && t.getUsers().size()!=0)).collect(Collectors.toSet());
        if(taskWithDetailsSet.size()==0){
            return "/pm/taskNotFound";
        }
        List<User> currentEmployees = userService.getAllUsersForCompany(projectService.getProjectWithDetails(id)
                .getCompany().getId());
        model.addAttribute("taskWithDetailsSet", taskWithDetailsSet);
        model.addAttribute("currentEmployees", currentEmployees);
        //mandatory for correct creation navigation bar
        model.addAttribute("currentProjectId", id);
        model.addAttribute("userRole", Role.PM);
        return "/pm/assignTasks";
    }

    @PreAuthorize("hasRole('PM')")
    @RequestMapping(path = "/show-all-task/for-{id}id", method = RequestMethod.GET)
    public String showAllTask(ModelMap model, @PathVariable("id") int id, Principal principal,HttpServletRequest request) {
        String remoteAddr = request.getRemoteAddr();
        int userId = userService.checkAccessUserToProject(principal.getName(),id);
        if(userId==0){
            return "redirect:/dashboard-pm";
        }
        Set<Task> taskWithDetailsSet =  new TreeSet<>(Comparator.comparing(Task::getId));
        for (Task task: taskService.findTaskForProject(id)){
            taskWithDetailsSet.add(taskService.getTaskWithDetailsById(task.getId()));
        }
        if(taskWithDetailsSet.size()==0){
            return "/pm/taskNotFound";
        }
        List<User> currentEmployees = userService.getAllUsersForCompany(projectService.getProjectWithDetails(id)
                .getCompany().getId());
        model.addAttribute("taskWithDetailsSet", taskWithDetailsSet);
        model.addAttribute("currentEmployees", currentEmployees);
        //mandatory for correct creation navigation bar
        model.addAttribute("currentProjectId", id);
        model.addAttribute("userRole", Role.PM);
        return "/pm/showAllTask";

    }

    @PreAuthorize("hasRole('PM')")
    @RequestMapping(path = "/report-download/for-{id}id", method = RequestMethod.GET)
    public void download(@PathVariable("id") int projectId, Principal principal,HttpServletResponse response){
        if(userService.checkAccessUserToProject(principal.getName(), projectId)==0){
            return;
        }
        List<Sprint> sprintList = sprintService.getSprintsForProjectWithDetails(projectId);
        List<User> userList = userService.getEmployeesForProject(projectId);
        List<Task> taskList = new ArrayList<>();
        sprintList.stream().forEach(s->{
            Set<Task> tList = s.getTasks();
            if(tList!=null){
                for(Task t : tList){
                    taskList.add(taskService.getTaskWithDetailsById(t.getId()));
                }
            }
        });
        String fileName = "report-"+projectId+"Id.xlsx";
        ExelWriter exelWriter = new ExelWriter(fileName, "report");
        exelWriter.addHeader("Sprint name","Sprint description","Plan finish date","Dependent sprint name", "Contain task(number)");
        exelWriter.addData(sprintList);
        exelWriter.addHeader("Task name","Task description","Priority","Plan finish date","Dependent tasks", "Users");
        exelWriter.addData(taskList);
        exelWriter.addHeader("Name","email","Sex");
        exelWriter.addData(userList);
        createResponse(response, exelWriter.write());
        if (new File("report-"+projectId+"Id.xlsx").exists()) {
            new File("report-"+projectId+"Id.xlsx").delete();
            LOGGER.info("Deleted the previous file");
        }
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

    private void createResponse(HttpServletResponse response, String fileName){
        File file = new File(fileName);
        try(InputStream inputStream = new BufferedInputStream(new FileInputStream(file))){
            String mimeType= URLConnection.guessContentTypeFromName(file.getName());
            if(mimeType==null){
                mimeType = "application/octet-stream";
            }
            response.setContentType(mimeType);
            response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
            response.setContentLength((int)file.length());
            FileCopyUtils.copy(inputStream, response.getOutputStream());// Closes both streams when done
        }catch (FileNotFoundException e){
            LOGGER.error("file  with name \""+ file.getName()+"\" not found", e);
        }
        catch (IOException e) {
            LOGGER.error(e);
        }
    }
}
