package com.rs.datvexe.controller;

import com.rs.datvexe.model.Location;
import com.rs.datvexe.model.Trip;
import com.rs.datvexe.service.BookingService;
import com.rs.datvexe.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private TripService tripService;
    @GetMapping("/search")
    public String getAllTripByDateAndDonAndTra(Model model, @Param("date") String date, @Param("don") String don, @Param("tra") String tra){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sqlDate = null;

        try {
            // Chuyển đổi chuỗi thành kiểu dữ liệu SQL Date
            sqlDate = new Date(dateFormat.parse(date).getTime());

            // In ra kiểu dữ liệu SQL Date đã chuyển đổi
            System.out.println("SQL Date: " + sqlDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Trip> list_trip = new ArrayList<>();
        list_trip = bookingService.getTripByDateAndDonAndTra(sqlDate, don, tra);
        model.addAttribute("list_trip", list_trip);
        return "Booking";
    }
    @GetMapping("/search/location")
    public String getRouteByIdTrip(Model model, @Param("id_trip") Integer id_trip){
        Trip trip = tripService.getById(id_trip);
        List<Location> list_location_di = new ArrayList<>();
        List<Location> list_location_ve = new ArrayList<>();
        if(trip.getDirection().equals("Đi")){
            list_location_di = bookingService.getRouteByIdRouteAndType(trip.getIdRoute(), "don");
            list_location_ve = bookingService.getRouteByIdRouteAndType(trip.getIdRoute(), "tra");
        }
        else {
            list_location_di = bookingService.getRouteByIdRouteAndType(trip.getIdRoute(), "tra");
            list_location_ve = bookingService.getRouteByIdRouteAndType(trip.getIdRoute(), "don");
        }

        model.addAttribute("list_route_di", list_location_di);
        model.addAttribute("list_route_ve", list_location_ve);
        return "Trip";
    }
}
