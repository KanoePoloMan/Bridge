package s21.web.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import s21.datasource.messages.MessageStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageDTO {
    private UUID chatUUID;
    private String sender;
    private String receiver;
    private String message;
    private String timestamp;
    private MessageStatus status;
}
