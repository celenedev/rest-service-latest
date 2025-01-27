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
@Table(name = "ATM")
public class Atm {

    @Id
    @Column(name = "ATM_ID", nullable = false)
    private Integer id;

    @Column(name = "NAME", nullable = false, length = 10)
    private String name;

    @Column(name = "LOCATION", nullable = false)
    private String location;

}
