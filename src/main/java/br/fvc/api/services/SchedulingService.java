package br.fvc.api.services;

import java.sql.Date;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import br.fvc.api.models.Reserve;
import br.fvc.api.repositories.ReserveRepository;

@Component
@EnableScheduling
public class SchedulingService {

    private final long HORA = 1000;
    // private final long HORA = 300000;

    @Autowired
    private ReserveRepository reserveRepository;

    @Autowired
    private SimpMessagingTemplate template;

    @Scheduled(fixedDelay = HORA)
    public void setStatus() {

        List<Reserve> reserves = this.reserveRepository.getDateEndRent("EM ANDAMENTO");

        long currentTimeMillis = System.currentTimeMillis();
        Date now = new Date(currentTimeMillis);

        for (Reserve reserve : reserves) {
            if (now.after(reserve.getData_fim_aluguel())) {
                reserve.setStatus("EM ATRASO");
                this.reserveRepository.save(reserve);
            }
        }

        template.convertAndSend("/topic/pushmessages", now);
    }
}
