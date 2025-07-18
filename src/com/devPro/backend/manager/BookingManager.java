package com.devPro.backend.manager;

import com.devPro.backend.entity.Booking;
import com.devPro.backend.entity.Customer;
import com.devPro.backend.entity.Room;
import com.devPro.backend.repository.BookingRepository;
import com.devPro.backend.repository.CustomerRepository;
import com.devPro.backend.repository.RoomRepository;
import com.devPro.interfaces.IBooking;
import com.devPro.interfaces.IFormatDate;
import com.devPro.interfaces.IRoomSchedule;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BookingManager implements IBooking, IFormatDate, IRoomSchedule {
    private BookingRepository bookingRepository;
    private Scanner scanner;
    private LookUpAvailableRooms lookUpAvailableRooms;
    private RoomRepository roomRepository;
    private CustomerRepository customerRepository;

    // Định dạng ngày
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public BookingManager() {
        bookingRepository = new BookingRepository();
        scanner = new Scanner(System.in);
        lookUpAvailableRooms = new LookUpAvailableRooms();
        roomRepository = new RoomRepository();
        customerRepository = new CustomerRepository();
    }

    public void bookingRoom() {
        LocalDate checkInDate = null;
        LocalDate checkOutDate = null;
        while (true) {
            try {
                System.out.print("Nhập ngày checkin (dd-MM-yyyy): ");
                String checkIn = formatDay(scanner.nextLine());
                checkInDate = LocalDate.parse(checkIn, formatter);

                System.out.print("Nhập ngày checkout (dd-MM-yyyy): ");
                String checkOut = formatDay(scanner.nextLine());
                checkOutDate = LocalDate.parse(checkOut, formatter);

                if (!checkInDate.isBefore(checkOutDate)) {
                    System.out.println("Ngày checkout phải sau ngày checkin.");
                    continue;
                }
                break;
            } catch (Exception e) {
                System.out.println("Ngày không đúng định dạng. Vui lòng nhập lại.");
            }
        }

        String id = String.format("BOOK%03d", bookingRepository.getBookings().size() + 1);
        String cusId = checkNameExist();
        String roomId = checkAvailableRooms(checkInDate, checkOutDate);

        writeFileBooking(id, cusId, roomId, checkInDate, checkOutDate);
    }

    public String checkNameExist() {
        List<Customer> customers = customerRepository.getCustomers();
        Map<String, Customer> customerMap = new HashMap<>();
        List<String> customerNames = new ArrayList<>();

        for (Customer customer : customers) {
            customerMap.put(customer.getName(), customer);
            customerNames.add(customer.getName());
        }

        System.out.print("Nhập tên khách hàng: ");
        String name = scanner.nextLine().trim();
        String cusId = "";

        if (!customerMap.containsKey(name)) {
            System.out.println("Chưa có khách hàng này, sẽ tạo mã mới.");
            cusId = String.format("CUST%03d", customerRepository.getCustomers().size() + 1);
            // Có thể thêm khách hàng mới tại đây nếu cần
        } else {
            System.out.println("Đã có khách hàng.");
            cusId = customerMap.get(name).getId();
        }

        return cusId;
    }

    public String checkAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate) {
        System.out.println("Danh sách phòng trống:");
        lookUpAvailableRooms.displayAvailableRooms(checkInDate, checkOutDate);
        System.out.print("Nhập mã phòng: ");
        return scanner.nextLine();
    }

    public void writeFileBooking(String id, String cusId, String roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        File f = new File(fileAddressBooking);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f, true))) {
            if (f.exists() && f.length() > 0) {
                bw.newLine();
            }

            String line = id + ";" + cusId + ";" + roomId + ";" +
                    checkInDate.format(formatter) + ";" +
                    checkOutDate.format(formatter);

            bw.write(line);
            System.out.println("Đã ghi thông tin đặt phòng.");
            writeFileRoomSchedule(id, cusId, roomId, checkInDate, checkOutDate);

        } catch (Exception e) {
            System.out.println("Lỗi khi ghi file booking:");
            e.printStackTrace();
        }
    }

    public void writeFileRoomSchedule(String id, String cusId, String roomId, LocalDate checkInDate, LocalDate checkOutDate) {
        File file = new File(fileAddressRoomSchedule);

        try (BufferedWriter bw1 = new BufferedWriter(new FileWriter(file, true))) {
            long amountDate = ChronoUnit.DAYS.between(checkInDate, checkOutDate);

            // checkin
            bw1.write(roomId + ";" + checkInDate.format(formatter) + ";checkin\n");

            // staying
            for (int i = 1; i < amountDate - 1; i++) {
                LocalDate stayDate = checkInDate.plusDays(i);
                bw1.write(roomId + ";" + stayDate.format(formatter) + ";staying\n");
            }

            // checkout
            bw1.write(roomId + ";" + checkOutDate.format(formatter) + ";checkout");

            System.out.println("Đã ghi lịch sử phòng (room schedule).");

        } catch (Exception e) {
            System.out.println("Lỗi khi ghi room schedule: ");
            e.printStackTrace();
        }
    }
}
