package com.devPro.backend.entity;

import com.devPro.interfaces.IFormatDate;

import java.time.LocalDate;

public class Booking implements IFormatDate {
    private String id;
    private Customer customer;
    private Room room;
    private LocalDate checkIn;
    private LocalDate checkOut;
    public Booking() {

    }
    public Booking(String id, Customer customer, Room room, LocalDate checkIn, LocalDate checkOut) {
        this.id = id;
        this.customer = customer;
        this.room = room;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
    }
    public Booking(String id){
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public Customer getCustomer() {
        return customer;
    }
    public Room getRoom() {
        return room;
    }
    public LocalDate getCheckIn() {
        return checkIn;
    }
    public LocalDate getCheckOut() {
        return checkOut;
    }
    public Booking findByCustomerId(String code) {
        if(code.equals(customer.getId())) {
            return this;
        }
        return null;
    }
    public Booking findByRoomId(String code) {
        if(code.equals(room.getId())) {
            return this;
        }
        return null;
    }
    @Override
    public String toString() {
        return "Booking [id=" + id + ", customer=" + customer.getId() + ", room=" + room.getId() + ", checkIn=" + checkIn.format(formatter)
                + ", checkOut=" + checkOut.format(formatter) + "]";
    }
}
