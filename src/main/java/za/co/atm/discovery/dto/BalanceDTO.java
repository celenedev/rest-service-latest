package za.co.atm.discovery.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class BalanceDTO {

    private ClientDTO client;
    private List<AccountDTO> accounts;
}

