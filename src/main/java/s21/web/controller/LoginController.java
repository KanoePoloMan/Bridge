package s21.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class LoginController {
    @GetMapping("/login")
    public String getAuthenticationPage() {
        return "login.html";
    }
    @GetMapping("/registration")
    public String getRegistrationPage() {
        return "registration.html";
    }
}
