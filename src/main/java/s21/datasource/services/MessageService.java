package s21.datasource.services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import javax.naming.NotContextException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import s21.datasource.exceptions.ChatException;
import s21.datasource.exceptions.FriendException;
import s21.datasource.messages.ChatMessageDAO;
import s21.datasource.repository.MessagesRepository;
import s21.web.model.ChatMessageDTO;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessagesRepository messagesRepository;
    private final UserService userService;
    private final FriendsService friendsService;

    private final DateTimeFormatter dateTimeFormatter;

    public List<ChatMessageDTO> getMessages(String name, String friendName) throws FriendException, ChatException {
        UUID chatUUID = friendsService.getChatUUID(name, 
                                                   friendName);

        List<ChatMessageDAO> messages = messagesRepository.findByChat(chatUUID);

        return messages.stream().map(message -> {
            return new ChatMessageDTO(message.getChat(),
                                      userService.getUsernameByUUID(message.getSender()),
                                      userService.getUsernameByUUID(message.getReceiver()),
                                      message.getMessage(),
                                      message.getTimestamp().format(dateTimeFormatter),
                                      message.getStatus());
        }).toList();
    }
    public void save(ChatMessageDTO message) throws NotContextException {
        if(message == null) throw new NotContextException("Message is null");
        ChatMessageDAO messageDAO = new ChatMessageDAO(0, 
                                                       message.getChatUUID(), 
                                                       userService.getUuidByUsername(message.getSender()), 
                                                       userService.getUuidByUsername(message.getReceiver()), 
                                                       message.getMessage(), 
                                                       LocalDateTime.parse(message.getTimestamp(), dateTimeFormatter), 
                                                       message.getStatus());

        messagesRepository.save(messageDAO);
    }
}
