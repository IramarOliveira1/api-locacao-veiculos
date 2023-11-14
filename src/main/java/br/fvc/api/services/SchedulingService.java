package br.fvc.api.services;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.security.Principal;
import java.sql.Date;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.util.MessageSupplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketExtension;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import br.fvc.api.config.TutorialHandler;
import br.fvc.api.models.Reserve;
import br.fvc.api.repositories.ReserveRepository;

@Configuration
@EnableScheduling
@Service
public class SchedulingService implements WebSocketSession {

    private final long HORA = 1000;
    // private final long HORA = 300000;

    @Autowired
    private ReserveRepository reserveRepository;

    // @Autowired
    // private SimpMessagingTemplate template;

    @Scheduled(fixedDelay = HORA)
    public void setStatus() throws Exception {

        // List<Reserve> reserves = this.reserveRepository.getDateEndRent("EM
        // ANDAMENTO");

        // long currentTimeMillis = System.currentTimeMillis();
        // Date now = new Date(currentTimeMillis);

        // for (Reserve reserve : reserves) {
        // if (now.after(reserve.getData_fim_aluguel())) {
        // reserve.setStatus("EM ATRASO");
        // this.reserveRepository.save(reserve);
        // }
        // }

        this.sendMessage(new TextMessage("OLa tudo bem"));

    }

    @Override
    public void sendMessage(WebSocketMessage<?> message) throws IOException {
        try {

            String teste = (String) message.getPayload();
            TutorialHandler handler = new TutorialHandler();

            handler.handleTextMessage(this, new TextMessage(teste));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String getId() {
        return "id=683b4c04-d79e-ac89-4c21-feb2510cafe5";
    }

    @Override
    public URI getUri() {
        return URI.create("uri=ws://localhost:8080/myHandler]");
    }

    @Override
    public HttpHeaders getHandshakeHeaders() {
        throw new UnsupportedOperationException("Unimplemented method 'getHandshakeHeaders'");
    }

    @Override
    public Map<String, Object> getAttributes() {
        throw new UnsupportedOperationException("Unimplemented method 'getAttributes'");
    }

    @Override
    public Principal getPrincipal() {
        throw new UnsupportedOperationException("Unimplemented method 'getPrincipal'");
    }

    @Override
    public InetSocketAddress getLocalAddress() {
        throw new UnsupportedOperationException("Unimplemented method 'getLocalAddress'");
    }

    @Override
    public InetSocketAddress getRemoteAddress() {
        throw new UnsupportedOperationException("Unimplemented method 'getRemoteAddress'");
    }

    @Override
    public String getAcceptedProtocol() {
        throw new UnsupportedOperationException("Unimplemented method 'getAcceptedProtocol'");
    }

    @Override
    public void setTextMessageSizeLimit(int messageSizeLimit) {
        throw new UnsupportedOperationException("Unimplemented method 'setTextMessageSizeLimit'");
    }

    @Override
    public int getTextMessageSizeLimit() {
        throw new UnsupportedOperationException("Unimplemented method 'getTextMessageSizeLimit'");
    }

    @Override
    public void setBinaryMessageSizeLimit(int messageSizeLimit) {
        throw new UnsupportedOperationException("Unimplemented method 'setBinaryMessageSizeLimit'");
    }

    @Override
    public int getBinaryMessageSizeLimit() {
        throw new UnsupportedOperationException("Unimplemented method 'getBinaryMessageSizeLimit'");
    }

    @Override
    public List<WebSocketExtension> getExtensions() {
        throw new UnsupportedOperationException("Unimplemented method 'getExtensions'");
    }

    @Override
    public boolean isOpen() {
        throw new UnsupportedOperationException("Unimplemented method 'isOpen'");
    }

    @Override
    public void close() throws IOException {
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }

    @Override
    public void close(CloseStatus status) throws IOException {
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }
}
