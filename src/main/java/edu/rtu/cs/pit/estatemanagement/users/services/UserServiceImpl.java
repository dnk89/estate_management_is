package edu.rtu.cs.pit.estatemanagement.users.services;

import edu.rtu.cs.pit.estatemanagement.users.data.UserRepository;
import edu.rtu.cs.pit.estatemanagement.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
