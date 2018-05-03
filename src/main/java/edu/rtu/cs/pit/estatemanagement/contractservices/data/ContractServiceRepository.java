package edu.rtu.cs.pit.estatemanagement.contractservices.data;

import edu.rtu.cs.pit.estatemanagement.contractservices.domain.ContractService;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ContractServiceRepository extends CrudRepository<ContractService, Long> {

    List<ContractService> findAll();

    Optional<ContractService> findByCode(String code);
}
