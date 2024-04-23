package com.rs.datvexe.model;

import jakarta.persistence.*;

import java.sql.Time;
import java.sql.Date;

@Entity
@Table(name = "bill")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "id_trip")
    private int idTrip;

    @Column(name = "date")
    private Date date;

    @Column(name = "time")
    private Time time;

    @Column(name = "id_client")
    private int idClient;

    @Column(name = "quantity")
    private int quantity;

    public Bill() {
    }

    public Bill(int id, int idTrip, Date date, Time time, int idClient, int quantity) {
        this.id = id;
        this.idTrip = idTrip;
        this.date = date;
        this.time = time;
        this.idClient = idClient;
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdTrip() {
        return idTrip;
    }

    public void setIdTrip(int idTrip) {
        this.idTrip = idTrip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Time getTime() {
        return time;
    }

    public void setTime(Time time) {
        this.time = time;
    }

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
