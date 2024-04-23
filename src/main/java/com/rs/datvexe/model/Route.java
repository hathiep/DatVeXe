package com.rs.datvexe.model;


import jakarta.persistence.*;

@Entity
@Table(name = "route")
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "id_trip")
    private int idTrip;

    @Column(name = "location")
    private String location;

    @Column(name = "type")
    private String type;

    public Route() {
    }

    public Route(int id, int idTrip, String location, String type) {
        this.id = id;
        this.idTrip = idTrip;
        this.location = location;
        this.type = type;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
