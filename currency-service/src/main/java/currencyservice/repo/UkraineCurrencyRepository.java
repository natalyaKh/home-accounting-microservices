package currencyservice.repo;

import currencyservice.model.UkraineCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UkraineCurrencyRepository extends JpaRepository<UkraineCurrency, Long> {

}
