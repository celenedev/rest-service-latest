package za.co.atm.discovery.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import za.co.atm.discovery.dto.*;
import za.co.atm.discovery.entity.AtmAllocation;
import za.co.atm.discovery.entity.ClientAccount;
import za.co.atm.discovery.entity.Denomination;
import za.co.atm.discovery.entity.DenominationType;
import za.co.atm.discovery.exceptionhandling.ATMException;
import za.co.atm.discovery.repository.ATMAllocationRepository;
import za.co.atm.discovery.repository.ATMRepository;
import za.co.atm.discovery.repository.AccountRepository;
import za.co.atm.discovery.repository.DenominationRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ATMTransactionService {

    public static final String CFCA = "CFCA";
    private final ClientService clientService;
    private final AccountService accountService;
    private final AccountRepository clientAccountRepository;
    private final ATMRepository atmRepository;
    private final ATMAllocationRepository atmAllocationRepository;
    private final DenominationRepository denominationRepository;

    public ATMTransactionService(ClientService clientService, AccountService accountService,
                                 AccountRepository clientAccountRepository,
                                 ATMRepository atmRepository,
                                 DenominationRepository denominationRepository,
                                 ATMAllocationRepository atmAllocationRepository) {
        this.clientService = clientService;
        this.accountService = accountService;
        this.clientAccountRepository = clientAccountRepository;
        this.atmRepository = atmRepository;
        this.denominationRepository = denominationRepository;
        this.atmAllocationRepository = atmAllocationRepository;
    }

    @Transactional
    public WithdrawalDTO withdraw(String clientId, String atmId, String accountNumber,
                                  String requiredAmount) {

        ClientDTO client = clientService.getClientDTO(clientId);

        ClientAccount account = accountService.getAccountByAccountNumber(accountNumber);

        List<DenominationDTO> denominationDTOList;

        // check if atm is valid
        if (atmRepository.findById(Long.valueOf(atmId)).isEmpty() ||
                atmAllocationRepository.findAllByAtm_Id(Integer.valueOf(atmId)).isEmpty()) {
            throw new ATMException("ATM not registered or unfunded");
        }

        // check if it is a transactional account
        if (Boolean.FALSE.equals(account.getAccountTypeCode().getTransactional())) {
            throw new ATMException("Not a transactional account");
        }

        //check if atm has correct amount
        List<AtmAllocation> allocations = atmAllocationRepository.findAllByAtm_Id(Integer.valueOf(atmId));

        int total = 0;
        if (!allocations.isEmpty()) {
            for (AtmAllocation allocation : allocations) {
                total += allocation.getCount() * allocation.getDenomination().getDenominationValue()
                        .intValue();
            }

            if (total < Integer.parseInt(requiredAmount)) {
                throw new ATMException("Amount not available, would you like to draw " + total);
            }
        }else{
            throw new ATMException("Amount not available, would you like to draw " + total);
        }

        // check if client has enough money
        if ((account.getAccountTypeCode()!= null && account.getAccountTypeCode().getAccountTypeCode() != null && account.getAccountTypeCode().getAccountTypeCode().equals("CHQ")
                && new BigDecimal(requiredAmount).compareTo(
                account.getDisplayBalance().add(new BigDecimal(10000))) > 0)
                ||
                (account.getAccountTypeCode()!= null && account.getAccountTypeCode().getAccountTypeCode() != null && !account.getAccountTypeCode().getAccountTypeCode().equals("CHQ")
                        && new BigDecimal(requiredAmount).compareTo(account.getDisplayBalance()) > 0)
        ) {
            throw new ATMException("Insufficient funds");
        } else {
            denominationDTOList = countCurrency(new BigDecimal(requiredAmount),
                    Integer.valueOf(atmId));

            // all ok, do the withdrawal
            // ATM allocation update is assumed to be out of scope
            account.setDisplayBalance(
                    account.getDisplayBalance().subtract(new BigDecimal(requiredAmount)));
            accountService.withdraw(account);
        }

        return new WithdrawalDTO(client, accountService.buildAccountDTO(account),
                denominationDTOList);
    }

    public BalanceDTO getTransactionalDetails(String clientId) {

        ClientDTO clientDTO = clientService.getClientDTO(clientId);

        List<AccountDTO> accountDTOList;
        List<ClientAccount> clientAccountList = accountService.getTransactionalAccountsForClient(
                Integer.valueOf(clientId));
        accountDTOList = accountService.buildAccountDTOList(clientAccountList);
        accountDTOList.sort((o1, o2) -> o2.getBalance().subtract(o1.getBalance()).intValue());
        return new BalanceDTO(clientDTO, accountDTOList);
    }

    public BalanceDTO getCurrencyDetails(String clientId) {

        ClientDTO clientDTO = clientService.getClientDTO(clientId);

        List<String> foreignAccountList = List.of(CFCA);
        List<ClientAccount> clientAccountList = clientAccountRepository.findByClientIdAndAccountTypeCode_AccountTypeCodeInOrderByDisplayBalanceDesc(
                Integer.valueOf(clientId), foreignAccountList);

        List<AccountDTO> accountDTOList = accountService.buildAccountDTOList(clientAccountList);

        accountDTOList.sort((o1, o2) -> o2.getZarBalance().subtract(o1.getZarBalance()).intValue());

        return new BalanceDTO(clientDTO, accountDTOList);
    }

    private List<DenominationDTO> countCurrency(BigDecimal amount, Integer atmId) {
        DenominationType denominationType = new DenominationType();
        denominationType.setDenominationTypeCode("N");
        List<Denomination> denominations = denominationRepository.findAllByDenominationTypeCodeOrderByDenominationValueDesc(
                denominationType);

        List<DenominationDTO> denominationList = new ArrayList<>();

        for (Denomination denomination : denominations) {

            if (amount.compareTo(denomination.getDenominationValue()) > -1) {

                int count = amount.divide(denomination.getDenominationValue()).intValue();

                AtmAllocation availableDenomination = atmAllocationRepository.findAtmAllocationsByDenomination_IdAndAtm_Id(
                        denomination.getId(), atmId);

                if (count < availableDenomination.getCount()) {
                    denominationList.add(DenominationDTO.builder()
                            .denominationId(Long.valueOf(denomination.getId()))
                            .denominationValue(denomination.getDenominationValue())
                            .count(count)
                            .build());
                    amount = amount.remainder(denomination.getDenominationValue());
                } else {
                    denominationList.add(DenominationDTO.builder()
                            .denominationId(Long.valueOf(denomination.getId()))
                            .denominationValue(denomination.getDenominationValue())
                            .count(availableDenomination.getCount())
                            .build());
                    amount = amount.subtract(denomination.getDenominationValue()
                            .multiply(new BigDecimal(availableDenomination.getCount())));
                }
            }
        }

        return denominationList;
    }
}

