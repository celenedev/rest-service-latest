package za.co.atm.discovery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "CLIENT")

public class Client {

    @Id
    @Column(name = "CLIENT_ID", nullable = false)
    private Integer id;

    @Column(name = "TITLE", length = 10)
    private String title;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SURNAME", length = 100)
    private String surname;

    @Column(name = "DOB", nullable = false)
    private LocalDate dob;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CLIENT_SUB_TYPE_CODE", nullable = false)
    private ClientSubType clientSubTypeCode;

}

