package edu.rtu.cs.pit.estatemanagement.users.data;

import edu.rtu.cs.pit.estatemanagement.users.domain.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {

    List<Role> findAll();

    Optional<Role> findByName(String name);
}
