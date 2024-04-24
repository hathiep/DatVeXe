package com.rs.datvexe.controller;

import com.rs.datvexe.model.Bill;
import com.rs.datvexe.model.Client;
import com.rs.datvexe.model.Location;
import com.rs.datvexe.model.Trip;
import com.rs.datvexe.service.BillService;
import com.rs.datvexe.service.BookingService;
import com.rs.datvexe.service.ClientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(path = "/")
public class BookingController {
    @Autowired
    private BookingService bookingService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private BillService billService;
    @GetMapping("/search")
    public String getAllTripByDateAndDonAndTra(Model model, @Param("date") String date, @Param("don") String don, @Param("tra") String tra, HttpSession session){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sqlDate = null;
        try {
            // Chuyển đổi chuỗi thành kiểu dữ liệu SQL Date
            sqlDate = new Date(dateFormat.parse(date).getTime());
            session.setAttribute("date", sqlDate);
            // In ra kiểu dữ liệu SQL Date đã chuyển đổi
            System.out.println("SQL Date: " + sqlDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Trip> list_trip = new ArrayList<>();
        list_trip = bookingService.getTripByDateAndDonAndTra(sqlDate, don, tra);
        model.addAttribute("list_trip", list_trip);
        session.setAttribute("list_trip", list_trip);
        return "Booking";
    }
    @GetMapping("/search/location/{id_trip}")
    public String getRouteByIdTrip(Model model, @PathVariable("id_trip") Integer id_trip, HttpSession session){
        List<Trip> list_trip = new ArrayList<>();
        list_trip = (List<Trip>) session.getAttribute("list_trip");
        Trip trip = new Trip();
        for(Trip t: list_trip)
            if(t.getId() == id_trip){
                trip = t;
                break;
            }
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
        model.addAttribute("trip", trip);
        session.setAttribute("trip", trip);
        return "Trip";
    }
    @GetMapping("/add-client-infor")
    public String getInforLocationAndQuantity(Model model, @RequestParam("location_di") String locationDi,
                                              @RequestParam("location_ve") String locationVe,
                                              @RequestParam("quantity") int quantity, HttpSession session){
        session.setAttribute("location_di", locationDi);
        session.setAttribute("location_ve", locationVe);
        session.setAttribute("quantity", quantity);

        return "Client";
    }
    @GetMapping("/confirm-payment")
    public String getInforClient(Model model, @ModelAttribute("client") Client client, HttpSession session){
        if(clientService.getByEmail(client.getEmail()) != null){
            int id = clientService.getByEmail(client.getEmail()).getId();
            client.setId(id);
        }
        session.setAttribute("client", client);
        model.addAttribute("error", "");
        model.addAttribute("client", client);
        model.addAttribute("trip", session.getAttribute("trip"));
        model.addAttribute("location_di", session.getAttribute("location_di"));
        model.addAttribute("location_ve", session.getAttribute("location_ve"));
        model.addAttribute("quantity", session.getAttribute("quantity"));
        int quantity = (Integer) session.getAttribute("quantity");
        Trip trip = (Trip) session.getAttribute("trip");
        int totalPrice = quantity * trip.getPrice();
        model.addAttribute("totalPrice", totalPrice);
        return "Bill";
    }
    @PostMapping("/confirm-payment")
    public String saveBill(HttpSession session){
        Client client = (Client) session.getAttribute("client");
        clientService.save(client);
        client = clientService.getByEmail(client.getEmail());
        Bill bill = new Bill();
        bill.setDate((Date) session.getAttribute("date"));
        bill.setCreateDate(Date.valueOf(LocalDate.now()));
        bill.setDon((String) session.getAttribute("location_di"));
        bill.setTra((String) session.getAttribute("location_ve"));
        Trip trip = (Trip) session.getAttribute("trip");
        bill.setIdTrip(trip.getId());
        bill.setIdClient(client.getId());
        bill.setQuantity((Integer) session.getAttribute("quantity"));
        bill.setTime(Time.valueOf(LocalTime.now()));
        billService.save(bill);
        System.out.println("Them thanh cong");
        return "Success";
    }
}
