package za.co.atm.discovery.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class ClientDTO {

    private Long id;
    private String title;
    private String name;
    private String surname;

}

