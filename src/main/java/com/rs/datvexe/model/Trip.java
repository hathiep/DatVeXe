package com.rs.datvexe.model;

import jakarta.persistence.*;

import java.sql.Time;

@Entity
@Table(name = "trip")
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "don")
    private String don;

    @Column(name = "tra")
    private String tra;

    @Column(name = "direction")
    private String direction;

    @Column(name = "bien_so")
    private String bienSo;

    @Column(name = "don_time")
    private Time donTime;

    @Column(name = "tra_time")
    private Time traTime;

    @Column(name = "phone")
    private String phone;

    @Column(name = "image")
    private String image;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "id_route")
    private int idRoute;

    public Trip() {
    }

    public Trip(int id, String name, String don, String tra, String direction, String bienSo, Time donTime, Time traTime, String phone, String image, int quantity, int idRoute) {
        this.id = id;
        this.name = name;
        this.don = don;
        this.tra = tra;
        this.direction = direction;
        this.bienSo = bienSo;
        this.donTime = donTime;
        this.traTime = traTime;
        this.phone = phone;
        this.image = image;
        this.quantity = quantity;
        this.idRoute = idRoute;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDon() {
        return don;
    }

    public void setDon(String don) {
        this.don = don;
    }

    public String getTra() {
        return tra;
    }

    public void setTra(String tra) {
        this.tra = tra;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getBienSo() {
        return bienSo;
    }

    public void setBienSo(String bienSo) {
        this.bienSo = bienSo;
    }

    public Time getDonTime() {
        return donTime;
    }

    public void setDonTime(Time donTime) {
        this.donTime = donTime;
    }

    public Time getTraTime() {
        return traTime;
    }

    public void setTraTime(Time traTime) {
        this.traTime = traTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(int idRoute) {
        this.idRoute = idRoute;
    }
}

