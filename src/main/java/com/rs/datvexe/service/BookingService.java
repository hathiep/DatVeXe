package com.rs.datvexe.service;

import com.rs.datvexe.model.Route;
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
    RouteService routeService;
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

    public List<Route> getRouteByIdTripAndType(Integer id_trip, String type){
        List<Route> list_route = routeService.getByIdTripAndType(id_trip, type);
        return list_route;
    }
}
