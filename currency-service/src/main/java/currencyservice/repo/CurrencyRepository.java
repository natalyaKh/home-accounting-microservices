package currencyservice.repo;

import currencyservice.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface CurrencyRepository extends JpaRepository<Currency, Long> {
    Currency findByCurrencyNameAndUserUuidAndDeleted(String currencyName, String user_uuid, boolean b);

    List<Currency> findAllByUserUuidAndDeleted(String userUuid, boolean b);

    Currency findByAbbrAndUserUuidAndDeleted(String abbr, String userUuid, boolean b);
}
