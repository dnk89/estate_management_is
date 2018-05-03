package edu.rtu.cs.pit.estatemanagement.contractservices.services;

import edu.rtu.cs.pit.estatemanagement.contractservices.data.ContractServiceRepository;
import edu.rtu.cs.pit.estatemanagement.contractservices.domain.AmountCalculationType;
import edu.rtu.cs.pit.estatemanagement.contractservices.domain.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractServiceServiceImpl implements ContractServiceService {

    @Autowired
    private ContractServiceRepository contractServiceRepository;

    @Override
    public List<ContractService> findAll() {
        return contractServiceRepository.findAll();
    }

    @Override
    public void createService(String code, String description, AmountCalculationType calculationType) throws Exception {
        if (code.isEmpty())
            throw new Exception("Pakalpojuma kods nevar būt tukšs");
        if (description.isEmpty())
            throw new Exception("Pakalpojuma nosaukums nevar būt tukšs");

        Optional<ContractService> findService = contractServiceRepository.findByCode(code);
        if (findService.isPresent())
            throw new Exception(String.format("Pakalpojums ar kodu %s jau eksistē", code));

        ContractService newContractService = new ContractService();
        newContractService.setCode(code);
        newContractService.setDescription(description);
        newContractService.setCalculationType(calculationType);

        contractServiceRepository.save(newContractService);
    }

    @Override
    public Optional<ContractService> findByCode(String code) {
        return contractServiceRepository.findByCode(code);
    }

    @Override
    public void editService(String code, String description, AmountCalculationType calculationType) throws Exception {
        if (code.isEmpty())
            throw new Exception("Pakalpojuma kods nevar būt tukšs");
        if (description.isEmpty())
            throw new Exception("Pakalpojuma nosaukums nevar būt tukšs");

        Optional<ContractService> findService = contractServiceRepository.findByCode(code);
        if (!findService.isPresent())
            throw new Exception(String.format("Pakalpojums ar kodu %s neeksistē", code));

        ContractService editContractService = findService.get();
        editContractService.setCode(code);
        editContractService.setDescription(description);
        editContractService.setCalculationType(calculationType);

        contractServiceRepository.save(editContractService);
    }

    @Override
    public void deleteService(String code) throws Exception {
        Optional<ContractService> findService = contractServiceRepository.findByCode(code);
        if (!findService.isPresent())
            throw new Exception(String.format("Pakalpojums ar kodu %s neeksistē", code));
        contractServiceRepository.delete(findService.get());
    }

    @Override
    public List<ContractService> findBy(String code, String description, AmountCalculationType calculationType) {

        List<ContractService> findServices = findBy(code, description);

        findServices = findServices.stream()
                .filter(service -> service.getCalculationType() == calculationType)
                .collect(Collectors.toList());

        return findServices;
    }

    @Override
    public List<ContractService> findBy(String code, String description) {
        List<ContractService> findServices = contractServiceRepository.findAll();

        if (!code.isEmpty()) {
            findServices = findServices.stream()
                    .filter(service -> service.getCode().indexOf(code) > -1)
                    .collect(Collectors.toList());
        }

        if (!description.isEmpty()) {
            findServices = findServices.stream()
                    .filter(service -> service.getDescription().indexOf(description) > -1)
                    .collect(Collectors.toList());
        }

        return findServices;
    }
}
