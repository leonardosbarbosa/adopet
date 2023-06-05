package br.com.leonardosbarbosa.adopet.services;

import br.com.leonardosbarbosa.adopet.entities.Role;
import br.com.leonardosbarbosa.adopet.entities.User;
import br.com.leonardosbarbosa.adopet.projections.UserDetailsProjection;
import br.com.leonardosbarbosa.adopet.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);
        if (result.size() == 0) {
            throw new UsernameNotFoundException("Email not found");
        }

        User user = new User();
        user.setEmail(result.get(0).getUsername());
        user.setPassword(result.get(0).getPassword());
        result.forEach(projection -> user.getRoles().add(new Role(projection.getRoleId(), projection.getAuthority())));

        return user;
    }
}
