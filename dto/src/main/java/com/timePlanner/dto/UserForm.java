package com.timePlanner.dto;


import lombok.Data;

@Data
public class UserForm  extends  User{
    private String confirmPassword;
    private Message message;

    public void formCorrected(){
        if(super.firstName!=null){
            super.firstName = super.firstName.trim();
        }
        if(super.lastName!=null){
            super.lastName = super.lastName.trim();
        }
        if(super.password!=null){
            super.password = super.password.trim();
        }
        if(super.email!=null){
            super.email = super.email.trim();
        }
        if(super.phone!=null){
            super.phone = super.phone.trim();
        }
    }

    @Override
    public String toString() {
        return super.toString() +"UserForm{" +
                "confirmPassword='" + confirmPassword + '\'' +
                "| message=" + message +
                '}';
    }
}
