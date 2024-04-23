package com.rs.datvexe.service;

import com.rs.datvexe.model.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class BookingService {
    @Autowired
    BillService billService;
    @Autowired
    ClientService clientService;
    @Autowired
    LocationService routeService;
    @Autowired
    TripService tripService;

    public List<Trip> getTripByDateAndDonAndTra(Date date, String don, String tra){
        List<Trip> list_trip = tripService.getByDonAndTra(don, tra);
        for(Trip trip : list_trip){
            int quantiy = billService.getQuantityByIdTripAndDate(trip.getId(), date);
            trip.setQuantity(trip.getQuantity() - quantiy);
        }
        return list_trip;
    }

    public List<com.rs.datvexe.model.Location> getRouteByIdRouteAndType(Integer id_route, String type){
        List<com.rs.datvexe.model.Location> list_location = routeService.getByIdRouteAndType(id_route, type);
        return list_location;
    }
}
