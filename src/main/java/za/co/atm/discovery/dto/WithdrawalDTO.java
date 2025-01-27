package za.co.atm.discovery.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class WithdrawalDTO {

    private ClientDTO client;
    private AccountDTO balance;
    private List<DenominationDTO> denomination;
}
