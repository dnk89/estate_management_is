package edu.rtu.cs.pit.estatemanagement.rates.data;

import edu.rtu.cs.pit.estatemanagement.rates.domain.Rate;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RateRepository extends CrudRepository<Rate, Long> {
    List<Rate> findAll();

    Optional<Rate> findByCode(String code);

    List<Rate> findByCodeLikeAndDescriptionLike(String code, String description);

    List<Rate> findByCodeLike(String code);

    List<Rate> findByDescriptionLike(String description);
}
