package s21.datasource.model.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class FriendException extends Exception {
    public FriendException(String message) {
        super(message);
    }
}
