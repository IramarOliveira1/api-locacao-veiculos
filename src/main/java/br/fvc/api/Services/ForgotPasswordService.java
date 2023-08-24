package br.fvc.api.Services;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private ForgotPasswordRepository forgotPasswordRepository;

    @Autowired
    private SendMailService sendMailService;

    public ResponseEntity<Object> forgotPassword(UserRequestDTO data) {
        try {

            User user = userRepository.findSendMail(data.email);

            if (user == null) {
                return ResponseEntity.status(404)
                        .body(new GenericResponseDTO(true, "Email n√£o encontrado!"));
            }

            Long code = new Date().getTime();
            LocalDateTime date = LocalDateTime.now().plusDays(1);

            this.deleteLastCode(user);

            String text = "Utilize o c√≥digo para redefinir sua senha " + code
                    + ". CÛdigo tem validade de 24 horas, clique no link a seguir: http://localhost:8080/user/login";

            var responseSendMail = sendMailService.sendMail(data.email, "Resete de senha", text);

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
                        .body(new GenericResponseDTO(true, "C√≥digo inv√°lido!"));
            }

            if (LocalDateTime.now().isAfter(forgotPassword.getCreated_at())) {
                return ResponseEntity.status(400)
                        .body(new GenericResponseDTO(false,
                                "CÛdigo expirado, solicite um novo cÛdigo clicando aqui: http://localhost:8080/user/login  "));
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
