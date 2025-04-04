package s21.datasource.messages;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Component;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Component
@Table(name="messages")
public class ChatMessageDAO {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id;
    private UUID chat;
    private UUID sender;
    private UUID receiver; 
    private String message;
    private LocalDateTime timestamp;
    private MessageStatus status;
}
