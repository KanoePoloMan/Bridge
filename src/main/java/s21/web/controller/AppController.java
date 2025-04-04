package s21.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
public class AppController {
    @GetMapping("/app")
    public String getAppPage() {
        return "app.html";
    }
    @GetMapping("/menu")
    public String getMenuPage() {
        return "menu.html";
    }
    
}
