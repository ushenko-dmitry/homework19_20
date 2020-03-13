package ru.mail.dimaushenko.webmodule.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import ru.mail.dimaushenko.webmodule.controller.model.UserForm;

@Component
public class UserFormValidator implements Validator{

    @Override
    public boolean supports(Class<?> type) {
        return UserForm.class.equals(type);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        ValidationUtils.rejectIfEmpty(errors, "username", "user.username.empty");
        ValidationUtils.rejectIfEmpty(errors, "password", "user.password.empty");
        ValidationUtils.rejectIfEmpty(errors, "passwordConfirm", "user.passwordConfirm.empty");
        UserForm userForm = (UserForm) obj;
        
        if (!userForm.getPassword().equals(userForm.getPasswordConfirm())){
            errors.rejectValue("qwerty", "qwerty");
        }
    }
    
}
