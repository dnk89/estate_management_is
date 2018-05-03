package edu.rtu.cs.pit.estatemanagement.rates.services;

import edu.rtu.cs.pit.estatemanagement.rates.data.RateRepository;
import edu.rtu.cs.pit.estatemanagement.rates.domain.Rate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RateServiceImpl implements RateService {

    @Autowired
    private RateRepository rateRepository;

    @Override
    public List<Rate> findAll() {
        return rateRepository.findAll();
    }

    @Override
    public void createRate(String code, String description, Double value) throws Exception {
        if (code.isEmpty())
            throw new Exception("Tarifa kods nevar būt tukšs");
        if (description.isEmpty())
            throw new Exception("Tarifa nosaukums nevar būt tukšs");

        Optional<Rate> findRate = rateRepository.findByCode(code);
        if (findRate.isPresent())
            throw new Exception(String.format("Tarifs ar kodu %s jau eksistē", code));

        Rate newRate = new Rate();
        newRate.setCode(code);
        newRate.setDescription(description);
        newRate.setValue(value);

        rateRepository.save(newRate);
    }

    @Override
    public Optional<Rate> findByCode(String code) {
        return rateRepository.findByCode(code);
    }

    @Override
    public void editRate(String code, String description, Double value) throws Exception {
        if (code.isEmpty())
            throw new Exception("Tarifa kods nevar būt tukšs");
        if (description.isEmpty())
            throw new Exception("Tarifa nosaukums nevar būt tukšs");

        Optional<Rate> findRate = rateRepository.findByCode(code);
        if (!findRate.isPresent())
            throw new Exception(String.format("Tarifs ar kodu %s neeksistē", code));

        Rate editRate = findRate.get();
        editRate.setCode(code);
        editRate.setDescription(description);
        editRate.setValue(value);

        rateRepository.save(editRate);
    }

    @Override
    public void deleteRate(String code) throws Exception {
        Optional<Rate> findRate = rateRepository.findByCode(code);
        if (!findRate.isPresent())
            throw new Exception(String.format("Tarifs ar kodu %s neeksistē", code));
        rateRepository.delete(findRate.get());
    }

    @Override
    public List<Rate> findBy(String code, String description) {

        if (code.isEmpty() && description.isEmpty()) {
            return rateRepository.findAll();
        }

        if (!code.isEmpty() && !description.isEmpty()) {
            return rateRepository.findByCodeLikeAndDescriptionLike(likePattern(code), likePattern(description));
        }

        if (!code.isEmpty()) {
            return rateRepository.findByCodeLike(likePattern(code));
        }

        return rateRepository.findByDescriptionLike(likePattern(description));
    }

    private String likePattern(String value) {
        return "%" + value + "%";
    }
}
