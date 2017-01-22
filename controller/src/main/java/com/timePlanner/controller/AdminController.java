package com.timePlanner.controller;

import com.timePlanner.controller.validator.CustomerFormValidator;
import com.timePlanner.controller.validator.UserFormValidator;
import com.timePlanner.dto.*;
import com.timePlanner.service.CompanyService;
import com.timePlanner.service.CustomerService;
import com.timePlanner.service.ProjectService;
import com.timePlanner.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController {
    private static final Logger LOGGER = LogManager.getLogger(AdminController.class);

    @Autowired
    @Qualifier(value = "workerFormValidator")
    public UserFormValidator workerFormValidator;

    @Autowired
    public CustomerFormValidator customerFormValidator;

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CustomerService customerService;

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/create-project", method = RequestMethod.GET)
    public String createProject(ModelMap model, Principal principal){
        User user = userService.getUserWithDetailsByEmail(principal.getName());
        List<User> currentPM = userService.getAllUsersForCompany(user.getCompany().getId())
                .stream()
                .filter(u-> (u.getRole()!= Role.ADMIN)&&(u.getRole()!= Role.EMPLOYEE)&&(u.getRole()!= Role.CUSTOMER)).collect(Collectors.toList());
        model.addAttribute("userRole", Role.ADMIN);
        model.addAttribute("projectForm", new Project());
        model.addAttribute("currentPM", currentPM);
        return "/admin/createProject";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/create-project", method = RequestMethod.POST)
    public String createProjectPOST(@ModelAttribute("projectForm") Project project, Principal principal){
        User user = userService.getUserWithDetailsByEmail(principal.getName());
        project.setCompany(user.getCompany());
        projectService.saveProject(project);
        return "redirect:/dashboard-adm";
    }


    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/create-worker", method = RequestMethod.GET)
    public String createProjectManager(ModelMap model, Principal principal){
        model.addAttribute("userRole", Role.ADMIN);
        model.addAttribute("userForm", new UserForm());
        return "/admin/createWorker";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/create-worker", method = RequestMethod.POST)
    public String createProjectManagerPOST(@ModelAttribute("userForm") UserForm userForm, BindingResult result, ModelMap model,
                                           Principal principal, HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        workerFormValidator.validate(userForm, result);
        if (result.hasErrors()) {
            model.addAttribute("userRole", Role.ADMIN);
            return "/admin/createWorker";
        }
        Company company = userService.getUserWithDetailsByEmail(principal.getName()).getCompany();
        User user = userForm;
        String password = RandomStringUtils.randomAlphanumeric(8);
        user.setPassword(password);
        user.setCompany(company);
        String emailBody = "Hello dear " + user.getFullName() + " you invited to company:"
                + company.getName()+ " as " + user.getRole()+"<br>Your password:"+ user.getPassword()
                +" <br><br> Regards, <br>Time Planer Admin";
        MailSender mailSender = MailSender.newBuilder()
                .setRecipientEmail(user.getEmail())
                .setSubject("Time Planer Greetings you")
                .setEmailBody(emailBody)
                .build();
        try {
            mailSender.sendMail();
            userService.saveUser(user);
            return "redirect:/dashboard-adm";
        } catch (MessagingException e) {
            LOGGER.info("Request to creating worker from " + remoteAddr +" was failed, because\n" +
                    "Message sending with credential to address" + user.getEmail() + "was not successful");
            result.rejectValue("message", "createWorker.errorMailSending");
            model.addAttribute("userRole", Role.ADMIN);
            return "/admin/createWorker";
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/create-customer", method = RequestMethod.GET)
    public String createCustomers(ModelMap model, Principal principal){
        Company company = companyService.getCompanyByUserEmail(principal.getName());
        model.addAttribute("userRole", Role.ADMIN);
        model.addAttribute("customerForm", new CustomerForm());
        model.addAttribute("projects", company.getProjects());
        return "/admin/createCustomer";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @RequestMapping(path = "/create-customer", method = RequestMethod.POST)
    public String createCustomers(@ModelAttribute("customerForm") CustomerForm customerForm, ModelMap model,
                                  BindingResult result, Principal principal, HttpServletRequest request){
        String remoteAddr = request.getRemoteAddr();
        customerFormValidator.validate(customerForm, result);
        if(result.hasErrors()){
            Company company = companyService.getCompanyByUserEmail(principal.getName());
            model.addAttribute("projects", company.getProjects());
            model.addAttribute("userRole", Role.ADMIN);
            return "/admin/createCustomer";
        }
        Company company = userService.getUserWithDetailsByEmail(principal.getName()).getCompany();
        User user = customerForm.getUser();
        String password = RandomStringUtils.randomAlphanumeric(8);
        user.setPassword(password);
        user.setRole(Role.CUSTOMER);
        user.setCompany(company);
        customerForm.setUser(user);
        String emailBody = "Hello dear " + user.getFullName() + " you invited like customer to company "+
                 company.getName()+" <br>Your password:"+ user.getPassword()
                +" <br><br> Regards, <br>Time Planer Admin";
        MailSender mailSender = MailSender.newBuilder()
                .setRecipientEmail(user.getEmail())
                .setSubject("Time Planer Greetings nwe customer")
                .setEmailBody(emailBody)
                .build();
        try {
            mailSender.sendMail();
            customerService.saveCustomer(customerForm);
            return "redirect:/dashboard-adm";
        } catch (MessagingException e) {
            LOGGER.info("Request to creating worker from " + remoteAddr +" was failed, because\n" +
                    "Message sending with credential to address" + user.getEmail() + "was not successful");
            result.rejectValue("message", "createWorker.errorMailSending");
            model.addAttribute("userRole", Role.ADMIN);
            return "/admin/createCustomer";
        }
    }


    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        webDataBinder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

}

