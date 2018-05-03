package edu.rtu.cs.pit.estatemanagement.customers.services;

import edu.rtu.cs.pit.estatemanagement.customers.data.CustomerRepository;
import edu.rtu.cs.pit.estatemanagement.customers.domain.Customer;
import edu.rtu.cs.pit.estatemanagement.customers.domain.CustomerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public void createCustomer(String code, String description, String phone, String email, String address, CustomerType customerType) throws Exception {
        if (code.isEmpty())
            throw new Exception("Klienta kods nevar būt tukšs");
        if (description.isEmpty())
            throw new Exception("Klienta nosaukums nevar būt tukšs");

        Optional<Customer> findCustomer = customerRepository.findByCode(code);
        if (findCustomer.isPresent())
            throw new Exception(String.format("Klients ar kodu %s jau eksistē", code));

        Customer newCustomer = new Customer();
        newCustomer.setCode(code);
        newCustomer.setDescription(description);
        newCustomer.setPhone(phone);
        newCustomer.setEmail(email);
        newCustomer.setAddress(address);
        newCustomer.setCustomerType(customerType);

        customerRepository.save(newCustomer);
    }

    @Override
    public Optional<Customer> findByCode(String code) {
        return customerRepository.findByCode(code);
    }

    @Override
    public void editCustomer(String code, String description, String phone, String email, String address, CustomerType customerType) throws Exception {
        if (code.isEmpty())
            throw new Exception("Klienta kods nevar būt tukšs");
        if (description.isEmpty())
            throw new Exception("Klienta nosaukums nevar būt tukšs");

        Optional<Customer> findCustomer = customerRepository.findByCode(code);
        if (!findCustomer.isPresent())
            throw new Exception(String.format("Klients ar kodu %s neeksistē", code));

        Customer editCustomer = findCustomer.get();
        editCustomer.setCode(code);
        editCustomer.setDescription(description);
        editCustomer.setPhone(phone);
        editCustomer.setEmail(email);
        editCustomer.setAddress(address);
        editCustomer.setCustomerType(customerType);

        customerRepository.save(editCustomer);
    }

    @Override
    public void deleteCustomer(String code) throws Exception {
        Optional<Customer> findCustomer = customerRepository.findByCode(code);
        if (!findCustomer.isPresent())
            throw new Exception(String.format("Klients ar kodu %s neeksistē", code));
        customerRepository.delete(findCustomer.get());
    }

    @Override
    public List<Customer> findBy(String code, String description, String phone, String email, String address) {
        List<Customer> findCustomers = customerRepository.findAll();

        if (!code.isEmpty())
            findCustomers = findCustomers.stream()
                                .filter(customer -> customer.getCode().indexOf(code) > -1)
                                .collect(Collectors.toList());

        if (!description.isEmpty())
            findCustomers = findCustomers.stream()
                    .filter(customer -> customer.getDescription().indexOf(description) > -1)
                    .collect(Collectors.toList());

        if (!phone.isEmpty())
            findCustomers = findCustomers.stream()
                    .filter(customer -> customer.getPhone().indexOf(phone) > -1)
                    .collect(Collectors.toList());

        if (!email.isEmpty())
            findCustomers = findCustomers.stream()
                    .filter(customer -> customer.getEmail().indexOf(email) > -1)
                    .collect(Collectors.toList());

        if (!address.isEmpty())
            findCustomers = findCustomers.stream()
                    .filter(customer -> customer.getAddress().indexOf(address) > -1)
                    .collect(Collectors.toList());

        return findCustomers;
    }

    @Override
    public List<Customer> findBy(String code, String description, String phone, String email, String address, CustomerType customerType) {
        List<Customer> findCustomers = findBy(code, description, phone, email, address);

        findCustomers = findCustomers.stream()
                .filter(customer -> customer.getCustomerType() == customerType)
                .collect(Collectors.toList());

        return findCustomers;
    }
}
