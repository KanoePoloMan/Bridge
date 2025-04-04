package s21.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

import s21.web.services.handlers.SocketConnectionHandler;


@Configuration
@EnableWebSocket
public class WebSocketMessagesConfig implements WebSocketConfigurer {
    @Autowired
    private SocketConnectionHandler socketConnectionHandler;
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketConnectionHandler, "/chat")
                .setAllowedOrigins("*");
    }
    
}
