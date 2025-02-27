package s21.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthenticationController {
    @GetMapping("/authentication")
    public String getAuthenticationPage() {
        return "authentication.html";
    }
    
}
