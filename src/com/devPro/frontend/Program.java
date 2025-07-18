package com.devPro.frontend;

import com.devPro.backend.manager.*;
import com.devPro.interfaces.IFormatDate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import static com.devPro.interfaces.IFormatDate.formatter;

public class Program  {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        loadMenu(sc);
    }
    public static void loadMenu(Scanner scanner) {
        while (true) {
            System.out.println("========== MENU ==========");
            System.out.println("1. Quản lý khách hàng");
            System.out.println("2. Quản lý phòng");
            System.out.println("3. Đặt phòng");
            System.out.println("4. Xem danh sách đặt phòng");
            System.out.println("5. Tra cứu phòng trống");
            System.out.println("0. Thoát chương trình");
            System.out.println(" ==========================");
            System.out.print("Mời nhập: ");
            try {
                int lc = Integer.parseInt(scanner.nextLine());
                switch (lc) {
                    case 1:
                        new CustomerManager().loadMenu();
                        break;
                    case 2:
                        new RoomManager().loadMenu();
                        break;
                    case 3:
                        new BookingManager().bookingRoom();
                        break;
                    case 4:
                        new ViewBookingManager().displayBooking();
                        break;
                    case 5:
                        LookUpAvailableRooms lookUp = new LookUpAvailableRooms();
                        LocalDate checkInDate = null;
                        LocalDate checkOutDate = null;
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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
                        lookUp.displayAvailableRooms(checkInDate, checkOutDate);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Nhập sai. Vui lòng nhập lại!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Bạn nhập sai định dạng số. Vui lòng nhập lại!");
            }
        }
    }

    public static String formatDay(String date){
        String [] parts = date.split("[-/]");
        String day = parts[0];
        String month = parts[1];
        String year = parts[2];
        if(day.length()==1)day="0"+day;
        if(month.length()==1)month="0"+month;
        return day+"-"+month+"-"+year;
    }
}
