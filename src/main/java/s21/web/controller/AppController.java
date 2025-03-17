package s21.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.websocket.server.PathParam;



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
    @GetMapping("/friendRequest/{friendName}")
    public String getMethodName(@PathParam(value="friendName") String name, Model model) {
        model.addAttribute("friendName", name);
        return "friend-request.html";
    }
    
}
