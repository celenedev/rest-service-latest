package za.co.atm.discovery.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class DenominationDTO {

    private Long denominationId;
    private BigDecimal denominationValue;
    private Integer count;
}
