package za.co.atm.discovery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.atm.discovery.entity.CreditCardLimit;

@Repository
public interface CreditLimitRepository extends JpaRepository<CreditCardLimit, Long> {

    CreditCardLimit findCreditCardLimitByClientAccountNumber(String clientAccountNumber);

}
