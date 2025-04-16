package s21.web.controller.v1;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import s21.domain.services.ServersService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/servers")
public class ServersControllerRest {
    private final ServersService serversService;

    @GetMapping("list")
    public List<String> getServersList() {
        return serversService.getUserServersNames(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    
}
