package za.co.atm.discovery.service;

import org.springframework.stereotype.Service;
import za.co.atm.discovery.dto.AccountDTO;
import za.co.atm.discovery.entity.ClientAccount;
import za.co.atm.discovery.entity.CreditCardLimit;
import za.co.atm.discovery.entity.CurrencyConversionRate;
import za.co.atm.discovery.exceptionhandling.ATMException;
import za.co.atm.discovery.repository.AccountRepository;
import za.co.atm.discovery.repository.ConversionRepository;
import za.co.atm.discovery.repository.CreditLimitRepository;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    public static final String CHQ = "CHQ";
    private final AccountRepository accountRepository;
    private final ConversionRepository conversionRepository;
    private final CreditLimitRepository creditLimitRepository;

    public AccountService(AccountRepository accountRepository, ConversionRepository conversionRepository,
                          CreditLimitRepository creditLimitRepository) {
        this.accountRepository = accountRepository;
        this.conversionRepository = conversionRepository;
        this.creditLimitRepository = creditLimitRepository;
    }

    public ClientAccount getAccountByAccountNumber(String accountNumber) {
        ClientAccount account = accountRepository.findByClientAccountNumber(accountNumber);

        if (account == null) {
            throw new ATMException("No accounts to display");
        } else {
            return account;
        }
    }

    public ClientAccount withdraw(ClientAccount account) {
        return accountRepository.save(account);
    }

    public List<ClientAccount> getTransactionalAccountsForClient(Integer clientId) {
        return accountRepository.findByClientIdAndAccountTypeCode_TransactionalIsTrueOrderByDisplayBalanceDesc(clientId);
    }

    public List<AccountDTO> buildAccountDTOList(List<ClientAccount> clientAccountList){

        List<AccountDTO> accountDTOList = new ArrayList<>();

        for (ClientAccount e : clientAccountList) {
            accountDTOList.add(buildAccountDTO(e));
        }
        return accountDTOList;
    }

    public AccountDTO buildAccountDTO(ClientAccount account) {

        CurrencyConversionRate rateInfo = conversionRepository.findCurrencyConversionRateByCurrencyCode(account.getCurrencyCode().getCurrencyCode());

        BigDecimal zarBalance;
        if (rateInfo.getConversionIndicator().equals("*")) {
            zarBalance = account.getDisplayBalance().multiply(rateInfo.getRate()).setScale(3, RoundingMode.HALF_DOWN);
        } else {
            zarBalance = account.getDisplayBalance().divide(rateInfo.getRate(), 3, RoundingMode.HALF_DOWN);
        }

        Optional<CreditCardLimit> limit = Optional.ofNullable(creditLimitRepository.findCreditCardLimitByClientAccountNumber(account.getClientAccountNumber()));

        return AccountDTO.builder()
                .accountNumber(account.getClientAccountNumber())
                .balance(account.getAccountTypeCode().getAccountTypeCode().equals(CHQ)?account.getDisplayBalance().setScale(3, RoundingMode.HALF_DOWN).add(new BigDecimal(10000 ).setScale(3, RoundingMode.HALF_DOWN)):account.getDisplayBalance().setScale(3, RoundingMode.HALF_DOWN))
                .accountTypeDescription(account.getAccountTypeCode().getDescription())
                .accountLimit(limit.map(CreditCardLimit::getAccountLimit).orElse(null))
                .conversionRate(rateInfo.getRate().setScale(3, RoundingMode.HALF_DOWN))
                .typeCode(account.getAccountTypeCode().getAccountTypeCode())
                .currencyCode(account.getCurrencyCode().getCurrencyCode())
                .zarBalance(zarBalance.setScale(3, RoundingMode.HALF_DOWN))
                .build();
    }
}

