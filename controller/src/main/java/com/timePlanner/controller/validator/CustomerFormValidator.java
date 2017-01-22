package com.timePlanner.controller.validator;


import com.timePlanner.dto.Customer;
import com.timePlanner.dto.CustomerForm;
import com.timePlanner.dto.UserForm;
import com.timePlanner.service.CompanyService;
import com.timePlanner.service.EmptyResultException;
import com.timePlanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CustomerFormValidator implements Validator {
    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Override
    public boolean supports(Class<?> aClass) {
        return Customer.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Customer customer = (CustomerForm) o;
        UserForm user =  new UserForm(customer.getUser());
        user.formCorrected();
        if ("".equals(user.getFirstName())) {
            errors.rejectValue("message", "error.firstNameWrong");
        } else if ("".equals(user.getLastName())) {
            errors.rejectValue("message", "error.lastNameWrong");
        } else if ("".equals(user.getEmail())) {
            errors.rejectValue("message", "error.email");
        } else if ("".equals(user.getPhone())) {
            errors.rejectValue("message", "error.phone");
        } else if (!phoneValid(user.getPhone())) {
            errors.rejectValue("message", "error.phone");
        } else if (emailExist(user.getEmail())) {
            errors.rejectValue("message", "error.emailExist");
        }
    }

    private boolean phoneValid(String phone){
        Pattern pattern = Pattern.compile("^((8|\\+3)[\\- ]?)?(\\(?\\d{3}\\)?[\\- ]?)?[\\d\\- ]{7,10}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.find();
    }

    private boolean emailExist(String email){
        try {
            userService.getUserByEmail(email);
            return true;
        } catch (EmptyResultException e) {
            return  false;
        }
    }

}