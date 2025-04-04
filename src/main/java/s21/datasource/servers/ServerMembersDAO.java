package s21.datasource.servers;

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
@Table(name="server_members")
@Entity
public class ServerMembersDAO {
    @Id
    private int id;
    @Column(name="server_uuid")
    private UUID server;
    @Column(name="user_uuid")
    private UUID user;
    private String state;
}
