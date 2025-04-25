package bts.sio.azurimmo.service;

import bts.sio.azurimmo.model.Utilisateur;
import bts.sio.azurimmo.repository.UtilisateurRepository;
import bts.sio.azurimmo.security.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UtilisateurRepository userRepository;

    public JpaUserDetailsService(@Autowired UtilisateurRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        Utilisateur user = userRepository.findByEmail(usernameOrEmail);
        if(user == null) {
            throw new UsernameNotFoundException(usernameOrEmail);
        }
        return new AuthUser(user);
    }
}