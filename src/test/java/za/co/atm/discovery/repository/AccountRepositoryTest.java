package za.co.atm.discovery.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.co.atm.discovery.entity.ClientAccount;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testSuccessfulFindByClientAccountNumber() {

        ClientAccount foundAccount = accountRepository.findByClientAccountNumber("1053664521");

        // Assert
        assertNotNull(foundAccount);
        assertEquals("SVGS", foundAccount.getAccountTypeCode().getAccountTypeCode());
    }

    @Test
    void testUnSuccessfulFindByClientAccountNumber() {

        ClientAccount foundAccount = accountRepository.findByClientAccountNumber("ABC");

        assertNull(foundAccount);
    }
}
