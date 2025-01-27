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
@Table(name = "CURRENCY_CONVERSION_RATE")
public class CurrencyConversionRate {

    @Id
    @Column(name = "CURRENCY_CODE", nullable = false, length = 3)
    private String currencyCode;

    @MapsId
    @OneToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CURRENCY_CODE", nullable = false)
    private Currency currency;

    @Column(name = "CONVERSION_INDICATOR", nullable = false, length = 1)
    private String conversionIndicator;

    @Column(name = "RATE", nullable = false, precision = 18, scale = 8)
    private BigDecimal rate;

}
