package br.fvc.api.config;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

public class TutorialHandler implements WebSocketHandler {

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'afterConnectionEstablished'");
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        String tutorial = (String) message.getPayload();
        Thread.sleep(1000);
        session.sendMessage(new TextMessage(tutorial));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'handleTransportError'");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        throw new UnsupportedOperationException("Unimplemented method 'afterConnectionClosed'");
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
        // throw new UnsupportedOperationException("Unimplemented method 'supportsPartialMessages'");
    }
    
}
