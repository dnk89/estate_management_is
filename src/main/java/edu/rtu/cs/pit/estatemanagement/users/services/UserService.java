package edu.rtu.cs.pit.estatemanagement.users.services;

import edu.rtu.cs.pit.estatemanagement.users.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    List<User> findAll();

    void createUser(String username, String password, String email, String roleName) throws Exception;

    void editUser(String username, String password, String email, String roleName) throws Exception;

    String encodePassword(String password);

    String decodePassword(String password);

    void deleteUser(String username) throws Exception;

    List<User> findBy(String username, String email, String role);
}
