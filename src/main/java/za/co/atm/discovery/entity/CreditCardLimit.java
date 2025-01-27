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
@Table(name = "CREDIT_CARD_LIMIT")
public class CreditCardLimit {

    @Id
    @Column(name = "CLIENT_ACCOUNT_NUMBER", nullable = false, length = 10)
    private String clientAccountNumber;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CLIENT_ACCOUNT_NUMBER", nullable = false)
    private ClientAccount clientAccount;

    @Column(name = "ACCOUNT_LIMIT", nullable = false, precision = 18, scale = 3)
    private BigDecimal accountLimit;

}
