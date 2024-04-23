package com.rs.datvexe.repository;

import com.rs.datvexe.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.util.Date;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    Bill getBillByDateAndTimeAndIdClient(Date date, Time time, Integer idClient);

    @Query(value = "SELECT SUM(quantity) FROM bill t WHERE t.id_trip = :id_trip AND t.date = :date", nativeQuery = true)
    int getQuantityByIdTripAndDate(@Param("id_trip") Integer id_trip, @Param("date") Date date);


}
