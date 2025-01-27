package za.co.atm.discovery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLIENT_ACCOUNT")
public class ClientAccount {

    @Id
    @Column(name = "CLIENT_ACCOUNT_NUMBER", nullable = false, length = 10)
    private String clientAccountNumber;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CLIENT_ID", nullable = false)
    private Client client;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "ACCOUNT_TYPE_CODE", nullable = false)
    private AccountType accountTypeCode;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CURRENCY_CODE", nullable = false)
    private Currency currencyCode;

    @Column(name = "DISPLAY_BALANCE", precision = 18, scale = 3)
    private BigDecimal displayBalance;
}

