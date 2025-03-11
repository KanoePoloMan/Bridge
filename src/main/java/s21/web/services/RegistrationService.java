package s21.web.services;


import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.security.auth.message.AuthException;
import lombok.RequiredArgsConstructor;
import s21.datasource.mapper.UserWebDatasourceMapper;
import s21.datasource.repository.UserRepository;
import s21.domain.model.Role;
import s21.web.mapper.UserDatasourceWebMapper;
import s21.web.model.UserDTO;
import s21.web.model.jwt.JwtRequest;
import s21.web.model.jwt.JwtResponse;

@Service
@RequiredArgsConstructor
public class RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final UserDatasourceWebMapper toWebMapper = UserDatasourceWebMapper.INSTANCE;
    private final UserWebDatasourceMapper toDatasourceMapper = UserWebDatasourceMapper.INSTANCE;

    public JwtResponse registration(JwtRequest request) throws AuthException {
        UserDTO user = toWebMapper.datasourceToWeb(userRepository.findByLogin(request.username()).orElse(null));

        if(user == null) {
            user = new UserDTO(UUID.randomUUID(), 
                               request.username(), 
                               passwordEncoder.encode(request.password()),
                               List.of(Role.USER));

            userRepository.save(toDatasourceMapper.webToDatasource(user));
        }
        throw new AuthException("Invalid registration. User already exists");
    }
}
