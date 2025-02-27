package s21.datasource;

import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Table(name="friends")
@Entity
public class FriendsDAO {
    @Id
    @Column(name="user_uuid")
    private UUID uuid;
    @Column(name="friend_uuid")
    private UUID friend;
    @Column(name="state")
    private short state;
}
