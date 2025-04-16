package s21.web.controller.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jakarta.security.auth.message.AuthException;
import s21.datasource.model.exceptions.ChatException;
import s21.datasource.model.exceptions.FriendException;

@ControllerAdvice
public class RequestAdvice {
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> handleBadCredentials(BadCredentialsException e) {
        return new ResponseEntity<>("Invalid password: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AuthException.class)
    public ResponseEntity<String> handleAuth(AuthException e) {
        return new ResponseEntity<>("Invalid authentication: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<String> handleNoAccess(AuthorizationDeniedException e) {
        return new ResponseEntity<>("У вас нет доступа к данному ресурсу. Пройдите авторизацию.", HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFound(UsernameNotFoundException e) {
        return new ResponseEntity<>("Пользователь не найден: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(FriendException.class)
    public ResponseEntity<String> handleFriendException(FriendException e) {
        return new ResponseEntity<>("Запись друзей не найдена: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ChatException.class)
    public ResponseEntity<String> handleChatException(ChatException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
