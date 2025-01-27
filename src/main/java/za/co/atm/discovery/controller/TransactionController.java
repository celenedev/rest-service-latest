package za.co.atm.discovery.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import za.co.atm.discovery.dto.BalanceDTO;
import za.co.atm.discovery.dto.WithdrawalDTO;
import za.co.atm.discovery.service.ATMTransactionService;

@RestController
@RequestMapping("/discovery-atm/")
public class TransactionController {

    private final ATMTransactionService atmTransactionService;

    public TransactionController(ATMTransactionService atmTransactionService) {
        this.atmTransactionService = atmTransactionService;
    }

    @GetMapping(value = "/queryTransactionalBalances/{clientId}", produces = {"application/json"})
    public BalanceDTO displayTransactionalAccounts(@PathVariable final String clientId) {
        return atmTransactionService.getTransactionalDetails(clientId);
    }

    @GetMapping(value = "/queryCcyBalances/{clientId}", produces = {"application/json"})
    public BalanceDTO displayCurrencyAccounts(@PathVariable final String clientId) {
        return atmTransactionService.getCurrencyDetails(clientId);
    }

    @GetMapping(value = "/withdraw/client/{clientId}/atm/{atmId}/account/{accountNumber}/amount/{requiredAmount}", produces = {"application/json"})
    public WithdrawalDTO withdraw(@PathVariable final String clientId,  @PathVariable final String atmId, @PathVariable final String accountNumber, @PathVariable final String requiredAmount) {
        return atmTransactionService.withdraw(clientId, atmId, accountNumber, requiredAmount);
    }

}

