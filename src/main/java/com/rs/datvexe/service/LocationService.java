package com.rs.datvexe.service;

import com.rs.datvexe.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    @Autowired
    LocationRepository locationRepository;
    public List<com.rs.datvexe.model.Location> getByIdRouteAndType(Integer id_route, String type){
        return locationRepository.getByIdRouteAndType(id_route, type);
    }
}
