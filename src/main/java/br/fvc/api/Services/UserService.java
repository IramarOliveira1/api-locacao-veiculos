package br.fvc.api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.User.UserDTO;
import br.fvc.api.Models.User;
import br.fvc.api.Repositories.UserRepository;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public String store(UserDTO data) {
        if (this.userRepository.findByLogin(data.email) != null) {
            return "já existe email";
        }

        // String encryptedPassword = new BCryptPasswordEncoder().encode(data.password);

        // User user = new User(data);

        // userRepository.save(user);

        return "não existe email";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username);
    }

}
