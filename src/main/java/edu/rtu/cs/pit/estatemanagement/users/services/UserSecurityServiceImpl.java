package edu.rtu.cs.pit.estatemanagement.users.services;

import edu.rtu.cs.pit.estatemanagement.users.data.UserRepository;
import edu.rtu.cs.pit.estatemanagement.users.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserSecurityServiceImpl implements UserDetailsService {

    private static final Logger LOG = LoggerFactory.getLogger(UserSecurityServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            String message = String.format("Username %s not found", username);
            LOG.warn(message);
            throw new UsernameNotFoundException(message);
        }
        return user;
    }
}
