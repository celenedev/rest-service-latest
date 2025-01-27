package za.co.atm.discovery.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import za.co.atm.discovery.dto.ClientDTO;
import za.co.atm.discovery.dto.WithdrawalDTO;
import za.co.atm.discovery.entity.*;
import za.co.atm.discovery.exceptionhandling.ATMException;
import za.co.atm.discovery.repository.ATMAllocationRepository;
import za.co.atm.discovery.repository.ATMRepository;
import za.co.atm.discovery.repository.DenominationRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@DataJpaTest
class ATMTransactionServiceTest {

    @Mock
    private ClientService clientService;

    @Mock
    private AccountService accountService;

    @Mock
    private ATMRepository atmRepository;

    @Mock
    private ATMAllocationRepository atmAllocationRepository;

    @Mock
    private DenominationRepository denominationRepository;

    @InjectMocks
    private ATMTransactionService withdrawalService;

    private ClientDTO clientDTO;
    private ClientAccount account;
    private AtmAllocation atmAllocation;
    private Atm atm;
    private Atm atmNoMoney;
    private Denomination denomination;
    private DenominationType denominationType;
    private AccountType accountType;
    private AccountType accountTypeNotTransactional;

    @BeforeEach
    void setUp() {
        clientDTO = new ClientDTO(1L, "Mr", "John", "Doe");

        accountType = new AccountType("CHQ", "Cheque account", true);
        accountTypeNotTransactional = new AccountType("CFGF", "Not transactional", false);
        account = new ClientAccount();
        account.setClientAccountNumber("123456");
        account.setDisplayBalance(new BigDecimal("5000.00"));
        account.setAccountTypeCode(accountType);

        atm = new Atm(1, "Sandton atm", "Location");
        atmNoMoney = new Atm(2, "ATM at Celene's house", "Location");
        denomination = new Denomination(1, new BigDecimal(1),denominationType);
        denominationType = new DenominationType("1", "N");

        atmAllocation = new AtmAllocation(1, atm, denomination,10000);
        atmAllocation.setDenomination(denomination);
    }

    @Test
    void testWithdraw_AtmNotRegistered() {
        Mockito.when(atmRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.empty());

        Exception exception = Assertions.assertThrows(ATMException.class, () -> withdrawalService.withdraw("1", "9", "123456", "100"));

        Assertions.assertEquals("ATM not registered or unfunded", exception.getMessage());
    }

    @Test
    void testWithdraw_AtmInsufficientFunds() {
        Mockito.when(accountService.getAccountByAccountNumber("123456")).thenReturn(account);
        Mockito.when(atmRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.ofNullable(atm));
        Mockito.when(atmAllocationRepository.findAllByAtm_Id(ArgumentMatchers.anyInt())).thenReturn(List.of(atmAllocation));

        Exception exception = Assertions.assertThrows(ATMException.class, () -> withdrawalService.withdraw("1", "1", "123456", "200000"));

        Assertions.assertEquals("Amount not available, would you like to draw " + atmAllocation.getCount(), exception.getMessage());
    }

    @Test
    void testWithdraw_AccountNotTransactional() {
        account.setAccountTypeCode(accountTypeNotTransactional);
        Mockito.when(accountService.getAccountByAccountNumber("123456")).thenReturn(account);
        Mockito.when(atmRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(atm));
        Mockito.when(atmAllocationRepository.findAllByAtm_Id(ArgumentMatchers.anyInt())).thenReturn(List.of(atmAllocation));

        Exception exception = Assertions.assertThrows(ATMException.class, () -> withdrawalService.withdraw("1", "1", "123456", "1000"));

        Assertions.assertEquals("Not a transactional account", exception.getMessage());
    }

    @Test
    void testWithdraw_InsufficientFundsInAccount() {
        Mockito.when(accountService.getAccountByAccountNumber("123456")).thenReturn(account);
        Mockito.when(atmRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(atm));
        atmAllocation.setCount(1000000);
        Mockito.when(atmAllocationRepository.findAllByAtm_Id(ArgumentMatchers.anyInt())).thenReturn(List.of(atmAllocation));

        Exception exception = Assertions.assertThrows(ATMException.class, () -> withdrawalService.withdraw("1", "1", "123456", "60000"));

        Assertions.assertEquals("Insufficient funds", exception.getMessage());
    }

    @Test
    void testWithdraw_SuccessfulWithdrawal() {
        Mockito.when(atmRepository.findById(1L)).thenReturn(Optional.of(atm));
        Mockito.when(atmAllocationRepository.findAllByAtm_Id(ArgumentMatchers.anyInt())).thenReturn(List.of(atmAllocation));
        Mockito.when(accountService.getAccountByAccountNumber("123456")).thenReturn(account);
        Mockito.when(clientService.getClientDTO("1")).thenReturn(clientDTO);

        WithdrawalDTO withdrawalDTO = withdrawalService.withdraw("1", "1", "123456", "100");

        Assertions.assertNotNull(withdrawalDTO);
    }
}
