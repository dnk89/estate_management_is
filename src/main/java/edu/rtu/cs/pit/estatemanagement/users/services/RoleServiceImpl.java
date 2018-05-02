package edu.rtu.cs.pit.estatemanagement.users.services;

import edu.rtu.cs.pit.estatemanagement.users.data.RoleRepository;
import edu.rtu.cs.pit.estatemanagement.users.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findById(int roleId) {
        return roleRepository.findById(roleId);
    }
}
