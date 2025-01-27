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
@Table(name = "DENOMINATION")
public class Denomination {

    @Id
    @Column(name = "DENOMINATION_ID", nullable = false)
    private Integer id;

    @Column(name = "DENOMINATION_VALUE", nullable = false, precision = 18, scale = 2)
    private BigDecimal denominationValue;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "DENOMINATION_TYPE_CODE")
    private DenominationType denominationTypeCode;

}
