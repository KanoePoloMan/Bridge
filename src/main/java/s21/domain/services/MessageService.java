package s21.domain.services;

import java.util.List;
import java.util.UUID;

import javax.naming.NotContextException;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import s21.datasource.mapper.ChatMessageWebDatasource;
import s21.datasource.model.exceptions.ChatException;
import s21.datasource.model.exceptions.FriendException;
import s21.datasource.model.messages.ChatMessageDAO;
import s21.datasource.repository.MessagesRepository;
import s21.web.mapper.ChatMessageDatasourceWeb;
import s21.web.model.ChatMessageDTO;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessagesRepository messagesRepository;
    private final FriendsService friendsService;

    private final ChatMessageDatasourceWeb toWebMapper;
    private final ChatMessageWebDatasource toDatasourceMapper;

    public List<ChatMessageDTO> getMessages(String name, String friendName) throws FriendException, ChatException {
        UUID chatUUID = friendsService.getChatUUID(name, 
                                                   friendName);

        List<ChatMessageDAO> messages = messagesRepository.findByChat(chatUUID);

        return toWebMapper.map(messages);
    }
    public void save(ChatMessageDTO message) throws NotContextException {
        if(message == null) throw new NotContextException("Message is null");

        messagesRepository.save(toDatasourceMapper.map(message));
    }
}