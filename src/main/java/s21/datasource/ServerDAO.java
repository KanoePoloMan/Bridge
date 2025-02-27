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
@Table(name="servers")
@Entity
public class ServerDAO {
    @Id
    private UUID uuid;
    @Column(name="owner_uuid")
    private UUID owner;
    private String name;
    @Column(name="is_open")
    private boolean isOpen;
}
