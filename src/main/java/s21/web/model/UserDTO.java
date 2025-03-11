package s21.web.model;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import s21.domain.model.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO implements UserDetails {
    private UUID uuid;
    private String username;
    private String password;
    private Collection<Role> authorities;
}
