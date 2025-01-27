package za.co.atm.discovery.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AccountDTO {

    private String accountNumber;
    private String typeCode;
    private String accountTypeDescription;
    private String currencyCode;
    private BigDecimal conversionRate;
    private BigDecimal balance;
    private BigDecimal zarBalance;
    private BigDecimal accountLimit;
}

