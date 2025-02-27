package s21.domain.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import s21.datasource.UserDAO;
import s21.datasource.repository.UserRepository;
import s21.web.model.SignUpRequest;

@Service
public class RegistrationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean registration(SignUpRequest request) {
        if(userRepository.findByLogin(request.username()).orElse(null) == null) {
            userRepository.save(new UserDAO(
                                        UUID.randomUUID(), 
                                        request.username(), 
                                        passwordEncoder.encode(request.password()))
                                );
            return true;
        }
        return false;
    }
}
