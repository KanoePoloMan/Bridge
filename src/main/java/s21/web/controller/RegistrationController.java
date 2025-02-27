package s21.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import s21.domain.services.RegistrationService;
import s21.web.model.SignUpRequest;

@Controller
public class RegistrationController {
    @Autowired
    private RegistrationService registrationService;


    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration.html";
    }
    @PostMapping("/registration")
    public String postRegistration(@ModelAttribute SignUpRequest request) {
        if(registrationService.registration(request)) return null;
        return "register.html";
    }
}
