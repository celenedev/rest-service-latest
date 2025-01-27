package za.co.atm.discovery.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLIENT_SUB_TYPE")
public class ClientSubType {

    @Id
    @Column(name = "CLIENT_SUB_TYPE_CODE", nullable = false, length = 4)
    private String clientSubTypeCode;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.RESTRICT)
    @JoinColumn(name = "CLIENT_TYPE_CODE", nullable = false)
    private ClientType clientTypeCode;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

}
