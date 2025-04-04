package s21.web.controller.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import s21.web.model.jwt.JwtRequest;
import s21.web.model.jwt.JwtResponse;
import s21.web.services.AuthenticationService;
import s21.web.services.RegistrationService;


@RestController
@RequiredArgsConstructor
public class LoginControllerRest {
    private final AuthenticationService authenticationService;
    private final RegistrationService registrationService;

    @PostMapping("/authentication")
    public ResponseEntity<JwtResponse> authentication(@RequestBody JwtRequest entity) throws AuthException {
        System.out.println(entity.username()  + " " + entity.password());
        final JwtResponse token = authenticationService.authentication(entity);
        return ResponseEntity.ok(token);
    }
    @PostMapping("/registration")
    public ResponseEntity<JwtResponse> registratuon(@RequestBody JwtRequest entity) throws AuthException {
        final JwtResponse token = registrationService.registration(entity);
        return ResponseEntity.ok(token);
    }
}
