package s21.web.model.jwt;

public record JwtRequest(
    String username, 
    String password
) {}
