package br.fvc.api.services;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.fvc.api.dtos.generic.GenericResponseDTO;
import br.fvc.api.dtos.user.UserRequestDTO;
import br.fvc.api.models.ForgotPassword;
import br.fvc.api.models.User;
import br.fvc.api.repositories.ForgotPasswordRepository;
import br.fvc.api.repositories.UserRepository;

@Service
public class ForgotPasswordService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    private SendMailService sendMailService;

    public ResponseEntity<Object> forgotPassword(UserRequestDTO data) {
        try {

            User user = userRepository.findSendMail(data.email);

            if (user == null) {
                return ResponseEntity.status(404)
                        .body(new GenericResponseDTO(true, "Email não encontrado!"));
            }

            Long code = new Date().getTime();
            LocalDateTime date = LocalDateTime.now().plusDays(1);

            this.deleteLastCode(user);

            String text = "Utilize o código para redefinir sua senha " + code
                    + ". Código tem validade de 24 horas, clique no link a seguir: https://alugue-aqui.netlify.app/recuperar-senha";

            var responseSendMail = sendMailService.sendMail(data.email, "Recuperar senha", text);

            ForgotPassword forgotPassword = new ForgotPassword();

            forgotPassword.setCreated_at(date);
            forgotPassword.setCode(code);
            forgotPassword.setUser(user);

            forgotPasswordRepository.save(forgotPassword);

            return ResponseEntity.status(responseSendMail.getStatusCode())
                    .body(new GenericResponseDTO(false, responseSendMail.getBody().toString()));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public ResponseEntity<Object> verifyCode(UserRequestDTO data) {
        try {
            ForgotPassword forgotPassword = forgotPasswordRepository.findByCode(data.code);

            if (forgotPassword == null) {
                return ResponseEntity.status(400)
                        .body(new GenericResponseDTO(true, "Código inválido!"));
            }

            if (LocalDateTime.now().isAfter(forgotPassword.getCreated_at())) {
                return ResponseEntity.status(400)
                        .body(new GenericResponseDTO(false,
                                "Código expirado, solicite um novo código clicando aqui: https://alugue-aqui.netlify.app/login"));
            }

            this.changePassword(forgotPassword.getUser(), data.password);

            return ResponseEntity.status(200)
                    .body(new GenericResponseDTO(false, "Senha trocada com sucesso!"));
        } catch (Exception e) {
            return ResponseEntity.status(400)
                    .body(new GenericResponseDTO(true, e.getMessage()));
        }
    }

    public void changePassword(User user, String password) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(password);

        user.setSenha(encryptedPassword);

        userRepository.save(user);

        this.deleteLastCode(user);
    }

    public void deleteLastCode(User user) {
        ForgotPassword id_user = forgotPasswordRepository.findByUser(user);

        if (id_user != null) {
            forgotPasswordRepository.delete(id_user);
        }
    }
}
