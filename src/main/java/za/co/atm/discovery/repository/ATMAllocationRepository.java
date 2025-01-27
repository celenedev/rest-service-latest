package za.co.atm.discovery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import za.co.atm.discovery.entity.AtmAllocation;

import java.util.List;

@Repository
public interface ATMAllocationRepository extends JpaRepository<AtmAllocation, Long> {

    List<AtmAllocation> findAllByAtm_Id(Integer atmId);

    AtmAllocation findAtmAllocationsByDenomination_IdAndAtm_Id(Integer denominationId, Integer atmId);

}
