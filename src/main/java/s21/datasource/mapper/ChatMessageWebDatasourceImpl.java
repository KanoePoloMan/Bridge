package s21.datasource.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import s21.datasource.model.messages.ChatMessageDAO;
import s21.domain.services.UserService;
import s21.web.model.ChatMessageDTO;

@Service
@RequiredArgsConstructor
public class ChatMessageWebDatasourceImpl implements ChatMessageWebDatasource {
    private final UserService userService;
    private final DateTimeFormatter dateTimeFormatter;

    @Override
    public ChatMessageDAO map(ChatMessageDTO domain) {
        if(domain == null) return null;
        return new ChatMessageDAO(0,
                                  domain.getChatUUID(), 
                                  userService.getUuidByUsername(domain.getSender()), 
                                  userService.getUuidByUsername(domain.getReceiver()), 
                                  domain.getMessage(), 
                                  LocalDateTime.parse(domain.getTimestamp(), dateTimeFormatter), 
                                  domain.getStatus());
    }

    @Override
    public List<ChatMessageDAO> map(List<ChatMessageDTO> domain) {
        if(domain == null) return null;

        return domain.stream().map(this::map).toList();
    }

}