package s21.web.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import s21.datasource.repository.UserRepository;
import s21.web.mapper.UserDatasourceWebMapper;
import s21.web.model.UserDTO;
import s21.web.model.jwt.JwtAuthentication;
import s21.web.model.jwt.JwtProvider;
import s21.web.model.jwt.JwtRequest;
import s21.web.model.jwt.JwtResponse;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    private final Map<String, String> refreshStorage = new HashMap<>();

    private final UserDatasourceWebMapper toWebMapper = UserDatasourceWebMapper.INSTANCE;

    public JwtResponse authentication(JwtRequest request) throws AuthException {
        final UserDTO user = toWebMapper.datasourceToWeb(userRepository.findByLogin(request.username()).orElse(null));

        if(user == null) throw new AuthException("No such user");

        if(passwordEncoder.matches(request.password(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);

            refreshStorage.put(user.getUsername(), refreshToken);

            return new JwtResponse(accessToken, refreshToken);
        } else {
            throw new BadCredentialsException("Incorrect password");
        }
    }
    public JwtResponse updateAccessToken(String refreshToken) {
        if(jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            System.out.println("updateAccessToken login: " + login);
            final String saveRefreshToken = refreshStorage.get(login);

            if(saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserDTO user = toWebMapper.datasourceToWeb(userRepository.findByLogin(login).orElse(null));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }
    public JwtResponse updateRefreshToken(String refreshToken) throws AuthException {
        if(jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
            final String login = claims.getSubject();
            final String saveRefreshToken = refreshStorage.get(login);

            if(saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserDTO user = toWebMapper.datasourceToWeb(userRepository.findByLogin(login).orElse(null));
                if(user == null) throw new AuthException("No such user or invalid token");
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String newRefreshToken = jwtProvider.generateRefreshToken(user);
                refreshStorage.put(login, newRefreshToken);
                return new JwtResponse(accessToken, newRefreshToken);
            }
        }
        throw new AuthException("Invalid JWT Token");
    }
    public JwtAuthentication getAuthentication() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
    public void addRefreshToken(String username, String token) {
        refreshStorage.put(username, token);
    }
}
