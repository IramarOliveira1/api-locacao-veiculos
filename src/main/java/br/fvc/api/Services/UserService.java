package br.fvc.api.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.fvc.api.Domain.User.UserDTO;
import br.fvc.api.Models.Address;
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
        // if (this.userRepository.findByEmail(data.email) != null) {
        //     return "j· existe email";
        // }

        System.out.println(data);
        // userRepository.save(data);
        // String encryptedPassword = new BCryptPasswordEncoder().encode(data.password);

        // User user = new User(data, encryptedPassword);

        // System.out.println(user.getId_endereco().getId());


        // user.setAddress(data.id_address);

        // userRepository.save(data.user);

        return "n√£o existe email";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username);
    }

}
