package com.timePlanner.controller.validator;

import com.timePlanner.dto.User;
import com.timePlanner.dto.UserForm;
import com.timePlanner.service.CompanyService;
import com.timePlanner.service.EmptyResultException;
import com.timePlanner.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserFormValidator implements Validator {

    private TypeForm type;

    public UserFormValidator(TypeForm type){
        this.type = type;
    }

    @Autowired
    private UserService userService;

    @Autowired
    private CompanyService companyService;

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //dependent of type form validate user because worker form don't have password field password generate automatically
        UserForm user = (UserForm) o;
        user.formCorrected();
        if(type == TypeForm.ADMIN) {
            if ("".equals(user.getFirstName())) {
                errors.rejectValue("message", "error.firstNameWrong");
            } else if ("".equals(user.getLastName())) {
                errors.rejectValue("message", "error.lastNameWrong");
            } else if ("".equals(user.getEmail())) {
                errors.rejectValue("message", "error.email");
            } else if ("".equals(user.getPhone())) {
                errors.rejectValue("message", "error.phone");
            } else if ("".equals(user.getCompany().getName())) {
                errors.rejectValue("message", "error.company");
            } else if (!phoneValid(user.getPhone())) {
                errors.rejectValue("message", "error.phone");
            } else if (!user.getConfirmPassword().equals(user.getPassword())) {
                errors.rejectValue("message", "error.passwordDontConfirm");
            } else if (user.getPassword().length() < 6) {
                errors.rejectValue("message", "error.passwordShort");
            } else if (emailExist(user.getEmail())) {
                errors.rejectValue("message", "error.emailExist");
            } else if (companyExist(user.getCompany().getName())) {
                errors.rejectValue("message", "error.companyExist");
            }
        }else if(type == TypeForm.WORKER){
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
            }else if (emailExist(user.getEmail())) {
                errors.rejectValue("message", "error.emailExist");
            }
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

    private boolean companyExist(String companyName){
        try{
            companyService.getCompanyByName(companyName);
            return true;
        }catch(EmptyResultException e){
            return  false;
        }
    }
}

