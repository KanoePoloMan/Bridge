package s21.web.services;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    private final UserDatasourceWebMapper toWebMapper = UserDatasourceWebMapper.INSTANCE;

    public JwtResponse authentication(JwtRequest request) throws AuthException {
        final UserDTO user = toWebMapper.datasourceToWeb(userRepository.findByLogin(request.username()).orElse(null));

        if(user == null) throw new AuthException("No such user");

        if(passwordEncoder.matches(request.password(), user.getPassword())) {
            final String accessToken = jwtProvider.generateAccessToken(user);

            return new JwtResponse(accessToken);
        } else {
            throw new BadCredentialsException("Incorrect password");
        }
    }
    public JwtAuthentication getAuthentication() {
        return (JwtAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
