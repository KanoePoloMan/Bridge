package s21.web.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {
    private final String type = "Bearer";

    private final String token;
}
