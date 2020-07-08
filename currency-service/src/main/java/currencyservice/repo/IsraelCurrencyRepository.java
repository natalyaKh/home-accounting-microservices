package currencyservice.repo;

import currencyservice.model.DefaultCurrency;
import currencyservice.model.IsraelCurrency;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IsraelCurrencyRepository extends JpaRepository<IsraelCurrency, Long> {

}
