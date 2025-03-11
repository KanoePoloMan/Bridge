package s21.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.security.auth.message.AuthException;

@ControllerAdvice
public class RequestAdvice {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException e) {
        return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<String> handleAuth(AuthException e) {
        return new ResponseEntity<>("Invalid authentication: " + e, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<String> handleNoAccess(AuthorizationDeniedException e) {
        return new ResponseEntity<>("У вас нет доступа к данному ресурсу. Пройдите авторизацию.", HttpStatus.UNAUTHORIZED);
    }
}
