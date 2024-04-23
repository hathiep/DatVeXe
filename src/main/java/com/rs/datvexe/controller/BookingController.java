package com.rs.datvexe.controller;

import com.rs.datvexe.model.Route;
import com.rs.datvexe.model.Trip;
import com.rs.datvexe.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/")
public class BookingController {
    @Autowired
    private BookingService bookingService;
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
    public String getRouteByIdTripAndType(Model model, @Param("id_trip") Integer id_trip, @Param("type") String type){
        List<Route> list_route = new ArrayList<>();
        list_route = bookingService.getRouteByIdTripAndType(id_trip, type);
        model.addAttribute("list_route", list_route);
        return "Trip";
    }
}
