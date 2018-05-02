package edu.rtu.cs.pit.estatemanagement.users.services;

import edu.rtu.cs.pit.estatemanagement.users.data.RoleRepository;
import edu.rtu.cs.pit.estatemanagement.users.data.UserRepository;
import edu.rtu.cs.pit.estatemanagement.users.domain.Role;
import edu.rtu.cs.pit.estatemanagement.users.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void createUser(String username, String password, String email, String roleName) throws Exception {
        if (username.isEmpty())
            throw new Exception("Lietotāja pieejas vārds nevar būt tukšs");
        if (password.isEmpty())
            throw new Exception("Lietotāja parole nevar būt tukša");
        if (email.isEmpty())
            throw new Exception("Lietotāja parole nevar būt tukša");
        Optional<Role> findRole = roleRepository.findByName(roleName);
        if (!findRole.isPresent())
            throw new Exception(String.format("Lietotāja tips %s nav atrasts", roleName));
        Optional<User> findUser = userRepository.findByUsername(username);
        if (findUser.isPresent())
            throw new Exception(String.format("Lietotājs ar pieejas vārdu %s jau eksistē", username));

        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(encodePassword(password));
        newUser.setEmail(email);
        newUser.setRole(findRole.get());

        userRepository.save(newUser);
    }

    @Override
    public void editUser(String username, String password, String email, String roleName) throws Exception {
        if (username.isEmpty())
            throw new Exception("Lietotāja pieejas vārds nevar būt tukšs");
        if (password.isEmpty())
            throw new Exception("Lietotāja parole nevar būt tukša");
        if (email.isEmpty())
            throw new Exception("Lietotāja parole nevar būt tukša");
        Optional<Role> findRole = roleRepository.findByName(roleName);
        if (!findRole.isPresent())
            throw new Exception(String.format("Lietotāja tips %s nav atrasts", roleName));
        Optional<User> findUser = userRepository.findByUsername(username);
        if (!findUser.isPresent())
            throw new Exception(String.format("Lietotājs ar pieejas vārdu %s neeksistē", username));

        User editUser = findUser.get();
        editUser.setUsername(username);
        editUser.setPassword(encodePassword(password));
        editUser.setEmail(email);
        editUser.setRole(findRole.get());

        userRepository.save(editUser);
    }

    @Override
    public String encodePassword(String password) {
        return "{noop}" + password;
    }

    @Override
    public String decodePassword(String password) {
        return password.replaceAll("\\{noop\\}", "");
    }
}
