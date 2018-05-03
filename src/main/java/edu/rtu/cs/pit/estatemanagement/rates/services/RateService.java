package edu.rtu.cs.pit.estatemanagement.rates.services;

import edu.rtu.cs.pit.estatemanagement.rates.domain.Rate;

import java.util.List;
import java.util.Optional;

public interface RateService {

    List<Rate> findAll();

    void createRate(String code, String description, Double value) throws Exception;

    Optional<Rate> findByCode(String code);

    void editRate(String code, String description, Double value) throws Exception;

    void deleteRate(String code) throws Exception;

    List<Rate> findBy(String code, String description);
}
