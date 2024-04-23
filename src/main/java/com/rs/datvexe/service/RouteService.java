package com.rs.datvexe.service;

import com.rs.datvexe.model.Route;
import com.rs.datvexe.repository.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RouteService {
    @Autowired
    RouteRepository routeRepository;
    public List<Route> getByIdTripAndType(Integer id_trip, String type){
        return routeRepository.getByIdTripAndType(id_trip, type);
    }
}
