package edu.rtu.cs.pit.estatemanagement.users.services;

import edu.rtu.cs.pit.estatemanagement.users.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserSecurityServiceImpl implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserSecurityServiceImpl.class);
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin";
    private static final String ADMIN_EMAIL = "dmitrij.kozlovskij@gmail.com";
    private static final String ADMIN_ROLE_NAME = "ROLE_ADMIN";

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findUser(username);
        if (!user.isPresent()) {
            String message = String.format("Username %s not found", username);
            LOG.warn(message);
            throw new UsernameNotFoundException(message);
        }
        return user.get();
    }

    private Optional<User> findUser(String username) {
        Optional<User> user = userService.findByUsername(username);
        if (!user.isPresent() && username.equalsIgnoreCase(ADMIN_USERNAME)) {
            try {
                userService.createUser(ADMIN_USERNAME, ADMIN_PASSWORD, ADMIN_EMAIL, ADMIN_ROLE_NAME);
            } catch (Exception e) {
                LOG.error("Nevar izveidot administratora noklusēto lietotāju: {}", e);
            }
            user = userService.findByUsername(username);
        }
        return user;
    }
}
