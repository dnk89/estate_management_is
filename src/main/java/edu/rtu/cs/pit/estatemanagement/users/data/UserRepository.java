package edu.rtu.cs.pit.estatemanagement.users.data;

import edu.rtu.cs.pit.estatemanagement.users.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    List<User> findAll();
}
