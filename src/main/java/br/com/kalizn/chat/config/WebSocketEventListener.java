package br.com.kalizn.chat.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import br.com.kalizn.chat.models.ChatMessageModel;
import br.com.kalizn.chat.models.MessageType;

@Component
public class WebSocketEventListener {
    
    private static final Logger log = LoggerFactory.getLogger(WebSocketEventListener.class);
    
    private final SimpMessageSendingOperations messageTemplate;

    public WebSocketEventListener(SimpMessageSendingOperations messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
        String username = (String) headerAccessor.getSessionAttributes().get("username");
        if (username != null) {
            log.info("user disconnected: {}", username);
            
            // Usando o Builder para criar o objeto ChatMessageModel
            ChatMessageModel chatMessage = new ChatMessageModel.Builder()
                .type(MessageType.LEAVE)
                .sender(username)
                .build();
            
            // Enviando a mensagem para o tópico '/topic/public'
            messageTemplate.convertAndSend("/topic/public", chatMessage);
        }
    }
}
