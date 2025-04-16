package s21.web.mapper;

import java.util.List;

import s21.datasource.model.messages.ChatMessageDAO;
import s21.web.model.ChatMessageDTO;

public interface ChatMessageDatasourceWeb {
    ChatMessageDTO map(ChatMessageDAO datasource);
    List<ChatMessageDTO> map(List<ChatMessageDAO> datasource);
}
