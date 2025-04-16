package s21.datasource.model.friends;

import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    @Column(name="user_uuid")
    private UUID uuid;
    @Column(name="friend_uuid")
    private UUID friend;
    @Column(name="state")
    private String state;
    @Column(name="chat_uuid")
    private UUID chat;
}
