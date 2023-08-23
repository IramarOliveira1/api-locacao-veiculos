package br.fvc.api.Services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Generic.GenericResponseDTO;
import br.fvc.api.Domain.User.UserRequestDTO;
import br.fvc.api.Models.ForgotPassword;
import br.fvc.api.Models.User;
import br.fvc.api.Repositories.ForgotPasswordRepository;
import br.fvc.api.Repositories.UserRepository;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    public ResponseEntity<Object> sendMail(UserRequestDTO data) {
        try {

            User user = userRepository.findSendMail(data.email);

            if (user == null) {
                return ResponseEntity.status(404)
                        .body(new GenericResponseDTO(false, "Email não encontrado!"));
            }

            Long code = new Date().getTime();

            ForgotPassword forgotPassword = new ForgotPassword();

            forgotPassword.setCode(code);
            forgotPassword.setUser(user);

            forgotPasswordRepository.save(forgotPassword);

            // forgotPasswordRepository.save(forgotPassword);

            // Boolean teste = encoder.matches(data.email, );

            // String text = "Para redefinir sua senha, clique no link
            // http://localhost:8080/user/login e use o código ("
            // + encoder.encode(user.) + ")";

            // if (user == null) {
            // return ResponseEntity.status(404)
            // .body(new GenericResponseDTO(false, "Email não encontrado!"));
            // }
            // SimpleMailMessage message = new SimpleMailMessage();

            // message.setFrom("alugue_aqui@suporte.com");
            // message.setTo(data.email);
            // message.setSubject("recuperar senha");
            // message.setText(text);

            // javaMailSender.send(message);

            return ResponseEntity.status(200)
                    .body(new GenericResponseDTO(false, "Email enviado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body(new GenericResponseDTO(true, e.getMessage()));
        }

    }

}
