package edu.rtu.cs.pit.estatemanagement.users.services;

import edu.rtu.cs.pit.estatemanagement.users.domain.User;
import org.springframework.stereotype.Service;

public interface UserService {

    User findByUsername(String username);
}
