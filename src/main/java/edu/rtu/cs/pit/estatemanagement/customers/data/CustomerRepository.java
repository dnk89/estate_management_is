package edu.rtu.cs.pit.estatemanagement.customers.data;

import edu.rtu.cs.pit.estatemanagement.customers.domain.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends CrudRepository<Customer, Long> {

    List<Customer> findAll();

    Optional<Customer> findByCode(String code);
}
