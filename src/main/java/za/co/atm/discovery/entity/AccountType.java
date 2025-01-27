package za.co.atm.discovery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ACCOUNT_TYPE")
public class AccountType {

    @Id
    @Column(name = "ACCOUNT_TYPE_CODE", nullable = false, length = 10)
    private String accountTypeCode;

    @Column(name = "DESCRIPTION", nullable = false, length = 50)
    private String description;

    @Column(name = "TRANSACTIONAL")
    private Boolean transactional;

}
