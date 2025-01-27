package za.co.atm.discovery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.atm.discovery.entity.Atm;

@Repository
public interface ATMRepository extends JpaRepository<Atm, Long> {

}
