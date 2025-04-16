package s21.domain.handlers;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import s21.domain.services.MessageService;
import s21.web.model.ChatMessageDTO;

@Component
@RequiredArgsConstructor
public class SocketConnectionHandler extends TextWebSocketHandler {
    private final Map<UUID, List<WebSocketSession>> sessions = Collections.synchronizedMap(new HashMap<>());
    private final MessageService messageService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        System.out.println(session.getId() + " " + session.getUri() + " Connected");
        
        URI uri = session.getUri();
        if(uri == null) throw new Exception("Empty URI");
        UUID chatUUID = UUID.fromString(splitArgs(uri.getQuery()).get("chatuuid"));

        sessions.putIfAbsent(chatUUID, Collections.synchronizedList(new ArrayList<>()));
        List<WebSocketSession> sessionsOfChat = sessions.get(chatUUID);
        sessionsOfChat.add(session);
    }
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        System.out.println(session.getId() + " " + session.getUri() + " Disconnected");
        
        URI uri = session.getUri();
        if(uri == null) throw new Exception("Empty URI");
        UUID chatUUID = UUID.fromString(splitArgs(uri.getQuery()).get("chatuuid"));

        List<WebSocketSession> sessionsOfChat = sessions.get(chatUUID);
        sessionsOfChat.remove(session);
        if(sessionsOfChat.isEmpty()) sessions.remove(chatUUID);
    }
    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message);

        System.out.println(session.getId() + " " + session.getUri() + " " + message.getPayload());

        ObjectMapper mapper = new ObjectMapper();
        ChatMessageDTO chatMessage = mapper.readValue(message.getPayload().toString(), 
                                            ChatMessageDTO.class);

        chatMessage.setSender(SecurityContextHolder.getContext().getAuthentication().getName());
        messageService.save(chatMessage);

        List<WebSocketSession> sessionsOfChat = sessions.get(chatMessage.getChatUUID());

        for(WebSocketSession chatSession : sessionsOfChat) {
            if(chatSession == session) continue;

            chatSession.sendMessage(message);
        }
    }
    private Map<String, String> splitArgs(String query) {
        if(query != null) {
            Map<String, String> result = new HashMap<>();
            String[] params = query.split("&");
            for(String param : params) {
                String[] variable = param.split("=");
                result.put(variable[0], variable[1]);
            }
            return result;
        }
        return new HashMap<>();
    }
}
