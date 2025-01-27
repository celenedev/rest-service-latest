package za.co.atm.discovery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.atm.discovery.entity.CurrencyConversionRate;

@Repository
public interface ConversionRepository extends JpaRepository<CurrencyConversionRate, Long> {

    CurrencyConversionRate findCurrencyConversionRateByCurrencyCode(String currencyCode);
}
