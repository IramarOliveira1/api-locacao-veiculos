package br.fvc.api.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Generic.GenericResponseDTO;

@Service
public class SendMailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public ResponseEntity<Object> sendMail(String to, String subject, String text) {

        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("alugue_aqui@suporte.com");
            message.setTo(to);
            message.setSubject("Recuperar senha");
            message.setText(text);

            javaMailSender.send(message);

            return ResponseEntity.status(200)
                    .body("E-mail enviado com sucesso!");
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

}
