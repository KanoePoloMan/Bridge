package s21.web.mapper;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import s21.datasource.model.messages.ChatMessageDAO;
import s21.domain.services.UserService;
import s21.web.model.ChatMessageDTO;

@Service
@RequiredArgsConstructor
public class ChatMessageDatasourceWebImpl implements ChatMessageDatasourceWeb {
    private final UserService userService;
    private final DateTimeFormatter dateTimeFormatter;

    @Override
    public ChatMessageDTO map(ChatMessageDAO datasource) {
        if(datasource == null) return null;
        return new ChatMessageDTO(datasource.getChat(), 
                                  userService.getUsernameByUUID(datasource.getSender()), 
                                  userService.getUsernameByUUID(datasource.getReceiver()), 
                                  datasource.getMessage(), 
                                  datasource.getTimestamp().format(dateTimeFormatter), 
                                  datasource.getStatus());
    }

    @Override
    public List<ChatMessageDTO> map(List<ChatMessageDAO> datasource) {
        if(datasource == null) return null;
        return datasource.stream().map(this::map).toList();
    }
}
