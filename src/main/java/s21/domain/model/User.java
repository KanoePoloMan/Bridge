package s21.domain.model;

import java.util.UUID;

import lombok.Getter;

@Getter
public class User {
    private UUID uuid;
    private String login;
}
