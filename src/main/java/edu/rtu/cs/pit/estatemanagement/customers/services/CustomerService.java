package edu.rtu.cs.pit.estatemanagement.customers.services;

import edu.rtu.cs.pit.estatemanagement.customers.domain.Customer;
import edu.rtu.cs.pit.estatemanagement.customers.domain.CustomerType;

import java.util.List;
import java.util.Optional;

public interface CustomerService {
    List<Customer> findAll();

    void createCustomer(String code, String description, String phone, String email, String address, CustomerType customerType) throws Exception;

    Optional<Customer> findByCode(String code);

    void editCustomer(String code, String description, String phone, String email, String address, CustomerType customerType) throws Exception;

    void deleteCustomer(String code) throws Exception;

    List<Customer> findBy(String code, String description, String phone, String email, String address);

    List<Customer> findBy(String code, String description, String phone, String email, String address, CustomerType customerType);
}
