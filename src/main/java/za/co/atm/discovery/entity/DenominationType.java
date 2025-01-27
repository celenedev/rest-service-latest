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
@Table(name = "DENOMINATION_TYPE")
public class DenominationType {

    @Id
    @Column(name = "DENOMINATION_TYPE_CODE", nullable = false, length = 1)
    private String denominationTypeCode;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

}
