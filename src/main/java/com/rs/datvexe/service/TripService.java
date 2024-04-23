package com.rs.datvexe.service;

import com.rs.datvexe.model.Trip;
import com.rs.datvexe.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TripService {
    @Autowired
    TripRepository tripRepository;

    public List<Trip> getByDonAndTra(String don, String tra){
        return tripRepository.getByDonAndTra(don, tra);
    }
    public Trip getById(Integer id){
        return tripRepository.getById(id);
    }
}
