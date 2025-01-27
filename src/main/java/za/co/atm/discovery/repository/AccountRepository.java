package za.co.atm.discovery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.atm.discovery.entity.ClientAccount;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<ClientAccount, Long> {

    //Transactional accounts
    List<ClientAccount> findByClientIdAndAccountTypeCode_TransactionalIsTrueOrderByDisplayBalanceDesc(Integer clientId);

    //All Accounts
    List<ClientAccount> findByClientIdAndAccountTypeCode_AccountTypeCodeInOrderByDisplayBalanceDesc(Integer clientId, List<String> foreignCurrencyAccounts);

    //Single account
    ClientAccount findByClientAccountNumber(String clientAccountNumber);

}
