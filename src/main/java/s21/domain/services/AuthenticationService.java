package s21.domain.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import s21.datasource.UserDAO;
import s21.datasource.repository.UserRepository;

@Service
public class AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean authorization(String username, String password) {
        UserDAO user = userRepository.findByLogin(username).orElse(null);

        if(user == null) return false;

        return passwordEncoder.matches(password, user.getPassword());
    }
}
