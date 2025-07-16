package com.devPro.backend.repository;

import com.devPro.backend.entity.Booking;
import com.devPro.backend.entity.Customer;
import com.devPro.backend.entity.Room;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BookingRepository {
    private List<Booking> bookings;

    public BookingRepository() {
        bookings = mockDataInFile();
    }

    public List<Booking> getBookings() {
        return bookings;
    }

    public List<Booking> mockDataInFile() {
        List<Booking> list= new ArrayList<Booking>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        try {
            File f= new File("C:\\Users\\Minhkz\\eclipse-workspace\\BTCK\\data\\bookings");
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            br.readLine();
            while((line=br.readLine())!= null) {
                String [] parts = line.split(";");
                String id= parts[0];
                Customer customer = new Customer(parts[1]);
                Room room = new Room(parts[2]);
                LocalDate checkIn= LocalDate.parse(parts[3], formatter);
                LocalDate checkOut= LocalDate.parse(parts[4], formatter);
                list.add(new Booking(id, customer, room, checkIn, checkOut));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
