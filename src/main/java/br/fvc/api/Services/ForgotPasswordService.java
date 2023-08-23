package br.fvc.api.Services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.Generic.GenericResponseDTO;
import br.fvc.api.Domain.User.ForgotRequestDTO;
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
                        .body(new GenericResponseDTO(true, "Email não encontrado!"));
            }

            Long code = new Date().getTime();

            ForgotPassword forgotPassword = new ForgotPassword();

            forgotPassword.setCode(code);
            forgotPassword.setUser(user);

            forgotPasswordRepository.save(forgotPassword);

            String text = "Utilize o código para redefinir sua senha " + code
                    + " clique no link a seguir: http://localhost:8080/user/login";

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom("alugue_aqui@suporte.com");
            message.setTo(data.email);
            message.setSubject("recuperar senha");
            message.setText(text);

            javaMailSender.send(message);

            return ResponseEntity.status(200)
                    .body(new GenericResponseDTO(false, "Email enviado com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body(new GenericResponseDTO(true, e.getMessage()));
        }

    }

    public ResponseEntity<Object> verifyToken(ForgotRequestDTO data) {

        ForgotPassword forgotPassword = forgotPasswordRepository.findByCode(data.code);

        if (forgotPassword == null) {
            return ResponseEntity.status(400)
                    .body(new GenericResponseDTO(true, "Código inválido!"));
        }

        this.changePassword(forgotPassword.getUser(), data.password);

        return ResponseEntity.status(200)
                .body(new GenericResponseDTO(false, "Email enviado com sucesso!"));
    }

    public void changePassword(User user, String password) {
        user.setSenha(password);

        userRepository.save(user);

    }

}
