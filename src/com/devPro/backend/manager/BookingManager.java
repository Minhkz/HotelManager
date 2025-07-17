package com.devPro.backend.manager;

import com.devPro.backend.entity.Booking;
import com.devPro.backend.entity.Customer;
import com.devPro.backend.entity.Room;
import com.devPro.backend.repository.BookingRepository;
import com.devPro.backend.repository.CustomerRepository;
import com.devPro.backend.repository.RoomRepository;
import com.devPro.interfaces.IBooking;
import com.devPro.interfaces.IFormatDate;

import java.time.LocalDate;
import java.util.*;

public class BookingManager implements IBooking, IFormatDate {
    private BookingRepository bookingRepository;
    private Scanner scanner;
    private LookUpAvailableRooms lookUpAvailableRooms;
    private RoomRepository roomRepository;
    private CustomerRepository customerRepository;

    public BookingManager() {
        bookingRepository = new BookingRepository();
        scanner = new Scanner(System.in);
        lookUpAvailableRooms = new LookUpAvailableRooms();
        roomRepository = new RoomRepository();
        customerRepository = new CustomerRepository();
    }

    public void bookingRoom(){
        LocalDate checkInDate=null;
        LocalDate checkOutDate=null;
        while (true){
            System.out.print("Nhập ngày checkin: ");
            String checkIn=formatDay(scanner.nextLine());
            checkInDate = LocalDate.parse(checkIn);
            System.out.print("Nhập ngày checkout: ");
            String checkOut=formatDay(scanner.nextLine());
            checkOutDate = LocalDate.parse(checkOut);
            if(checkInDate.isAfter(checkOutDate)){
                System.out.println("Nhập sai. Vui lòng nhập lại!");
            }
            break;
        }
        List<String> unavailableRooms= lookUpAvailableRooms.lookUpAvailableRoom(checkInDate,checkOutDate);
        for(String roomName : unavailableRooms){
            System.out.println(roomName);
        }
        List<Customer> customers = customerRepository.getCustomers();
        Map<String, Customer> customerMap= new HashMap<>();
        List<String> customerNames = new ArrayList<>();
        for(Customer customer : customers){
            customerMap.put(customer.getName(), customer);
            customerNames.add(customer.getName());
        }

        String id= String.format("BOOK%03d",bookingRepository.getBookings().size()+1);
        System.out.print("Nhập tên: ");
        String name=scanner.nextLine();
        String cusId="";
        if(!customerNames.contains(name)){
            cusId=String.format("CUST%03d",customerRepository.getCustomers().size()+1);
        }else {
            cusId=customerMap.get(name).getId();
        }

    }

}
