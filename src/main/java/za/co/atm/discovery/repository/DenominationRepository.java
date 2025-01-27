package za.co.atm.discovery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.atm.discovery.entity.Denomination;
import za.co.atm.discovery.entity.DenominationType;

import java.util.List;

@Repository
public interface DenominationRepository extends JpaRepository<Denomination, Long> {

    List<Denomination>  findAllByDenominationTypeCodeOrderByDenominationValueDesc(DenominationType denominationTypeCode);

}
