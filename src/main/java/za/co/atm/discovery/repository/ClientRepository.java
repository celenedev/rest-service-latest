package za.co.atm.discovery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.atm.discovery.entity.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

}
