package com.devPro.backend.manager;

import com.devPro.backend.entity.Booking;
import com.devPro.backend.repository.BookingRepository;
import com.devPro.interfaces.IBooking;

import java.util.ArrayList;
import java.util.List;

public class ViewBookingManager implements IBooking {
    private BookingRepository bookingRepository;

    public ViewBookingManager(){
        bookingRepository = new BookingRepository();
    }
    public void displayBooking(){
        List<Booking> bookings = bookingRepository.getBookings();
        for (Booking booking : bookings){
            System.out.println(booking.toString());
        }
    }
}
