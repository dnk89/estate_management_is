package edu.rtu.cs.pit.estatemanagement.users.data;

import edu.rtu.cs.pit.estatemanagement.users.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

}
