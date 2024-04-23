package com.rs.datvexe.service;

import com.rs.datvexe.model.Bill;
import com.rs.datvexe.model.Client;
import com.rs.datvexe.repository.BillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.sql.Date;

@Service
public class BillService {
    @Autowired
    BillRepository billRepository;
    public void save(Bill bill){
        billRepository.save(bill);
    }
    public Bill getBillByDateAndTimeAndIdClient(Date date, Time time, Integer id_client){
        return  billRepository.getBillByDateAndTimeAndIdClient(date, time, id_client);
    }
    public Integer getQuantityByIdTripAndDate(Integer id_trip, Date date){
        int quantity =0;
        if(billRepository.getQuantityByIdTripAndDate(id_trip, date) != null)
            quantity = billRepository.getQuantityByIdTripAndDate(id_trip, date);
        System.out.println(quantity);
        return  quantity;
    }

}
