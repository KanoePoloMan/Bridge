package s21.web.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RequestAdvice {
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<String> handleNoAccess(AuthorizationDeniedException e) {
        return new ResponseEntity<>("У вас нет доступа к данному ресурсу. Пройдите авторизацию.", HttpStatus.UNAUTHORIZED);
    }
}
