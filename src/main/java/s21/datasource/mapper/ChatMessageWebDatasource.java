package s21.datasource.mapper;

import java.util.List;

import s21.datasource.model.messages.ChatMessageDAO;
import s21.web.model.ChatMessageDTO;

public interface ChatMessageWebDatasource {
    ChatMessageDAO map(ChatMessageDTO domain);
    List<ChatMessageDAO> map(List<ChatMessageDTO> datasource);
}
