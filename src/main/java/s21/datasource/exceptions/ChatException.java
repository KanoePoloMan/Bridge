package s21.datasource.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ChatException extends Exception {
    public ChatException(String message) {
        super(message);
    }
}
