package com.rs.datvexe.controller;

import com.rs.datvexe.model.*;
import com.rs.datvexe.service.BillService;
import com.rs.datvexe.service.BookingService;
import com.rs.datvexe.service.ClientService;
import com.rs.datvexe.service.MailSenderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping(path = "/api")
public class BookingController {
    @Autowired
    MailSenderService mailSenderService;
    @Autowired
    private BookingService bookingService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private BillService billService;
    @GetMapping("/trips")
    public List<Trip> getAllTripByDateAndDonAndTra(@RequestParam("date") String date,
                                                   @RequestParam("don") String don,
                                                   @RequestParam("tra") String tra, HttpSession session){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date sqlDate = null;
        try {

            sqlDate = new Date(dateFormat.parse(date).getTime());
            session.setAttribute("date", sqlDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<Trip> list_trip = new ArrayList<>();
        list_trip = bookingService.getTripByDateAndDonAndTra(sqlDate, don, tra);
        session.setAttribute("list_trip", list_trip);
        return list_trip;
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
        MailStructure mailStructure = new MailStructure();
        mailStructure.setSubject("THÔNG BÁO BILL ĐẶT VÉ XE");
        String message = "DEAR " + client.getName() +"\n"+
                "Chúng tôi xác nhận bạn đã đặt vé xe của nhà xe " + trip.getName() + " gồm các thông tin sau :\n"+
                "   - Biển số xe: " + trip.getBienSo()+ "\n" +
                "   - Khởi hành: "+ trip.getDon() + " tại " + session.getAttribute("location_di") + " vào lúc: " + trip.getDonTime() +"\n"+
                "   - Điểm đến: "+ trip.getTra() + " tại " + session.getAttribute("location_ve") + " vào lúc: " + trip.getTraTime() +"\n"+
                "   - Giá vé: " + trip.getPrice() +"đ/vé \n" +
                "   - Số lượng vé: " + bill.getQuantity() +"\n"+
                "   - Tổng tiền đã thanh toán: "+ trip.getPrice() * bill.getQuantity() + "đ\n"+
                "\nVui lòng liên hệ nhà xe qua số điện thoại "+ trip.getPhone() + " nếu có bất kì thắc mắc nào\n\n"+
                "CẢM ƠN ĐÃ SỬ DỤNG DỊCH VỤ CỦA CHÚNG TÔI";
        mailStructure.setMessage(message);

        mailSenderService.sendMail(client.getEmail(),mailStructure);
        return "Success";
    }
}
