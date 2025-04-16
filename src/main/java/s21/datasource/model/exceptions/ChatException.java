package s21.datasource.model.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ChatException extends Exception {
    public ChatException(String message) {
        super(message);
    }
}
