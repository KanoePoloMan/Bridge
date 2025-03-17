package s21.datasource.services;

import java.util.UUID;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import s21.datasource.UserDAO;
import s21.datasource.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UUID getUuidByUsername(String username) {
        UserDAO user = userRepository.findByLogin(username).orElse(null);

        if(user == null) throw new UsernameNotFoundException("No such user");
        return user.getUuid();
    }
    public String getUsernameByUUID(UUID uuid) {
        UserDAO user = userRepository.findByUuid(uuid).orElse(null);

        if(user == null) throw new UsernameNotFoundException("No such user");
        return user.getLogin();
    }
    public UserDAO getUserByUUID(UUID uuid) {
        UserDAO user = userRepository.findByUuid(uuid).orElse(null);
        if(user == null) throw new UsernameNotFoundException("No such user");
        return user;
    }
}
