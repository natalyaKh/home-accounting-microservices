package currencyservice.repo;


import currencyservice.model.DefaultCurrency;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BaseCurrencyRepository extends JpaRepository<DefaultCurrency, Long> {

}
