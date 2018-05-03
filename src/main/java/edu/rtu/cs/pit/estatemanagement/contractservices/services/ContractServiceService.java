package edu.rtu.cs.pit.estatemanagement.contractservices.services;

import edu.rtu.cs.pit.estatemanagement.contractservices.domain.AmountCalculationType;
import edu.rtu.cs.pit.estatemanagement.contractservices.domain.ContractService;

import java.util.List;
import java.util.Optional;

public interface ContractServiceService {
    List<ContractService> findAll();

    void createService(String code, String description, AmountCalculationType calculationType) throws Exception;

    Optional<ContractService> findByCode(String code);

    void editService(String code, String description, AmountCalculationType calculationType) throws Exception;

    void deleteService(String code) throws Exception;

    List<ContractService> findBy(String code, String description, AmountCalculationType calculationType);

    List<ContractService> findBy(String code, String description);
}
