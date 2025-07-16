package com.devPro.backend.manager;

import com.devPro.backend.entity.Room;
import com.devPro.backend.entity.RoomSchedule;
import com.devPro.backend.repository.RoomRepository;
import com.devPro.backend.repository.RoomScheduleRepository;
import com.devPro.interfaces.IRoom;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RoomManager implements IRoom {
    private Scanner scanner;
    private RoomRepository roomRepository;
    public RoomManager() {
        scanner = new Scanner(System.in);
        roomRepository = new RoomRepository();
    }

    public void loadMenu() {
        roomProgram : do {
            System.out.println(" ========== MENU ==========");
            System.out.println("1. Hiển thị danh sách tất cả phòng");
            System.out.println("2. Tra cứu phòng trống theo loại và thời gian lưu trú");
            System.out.println("0. Thoát chức năng");
            System.out.println("=========================");
            System.out.print("Mời nhập: ");
            int opp= Integer.parseInt(scanner.nextLine());
            switch (opp) {
                case 1:
                    getRoomList();
                    break;
                case 2:
                    subRoomProgram : while(true) {
                        System.out.print("Nhập kiểu tra cứu phòng(type/time): ");
                        String typeRoom=scanner.nextLine();
                        switch (typeRoom) {
                            case "type":
                                System.out.print("Nhập kiểu phòng(SINGLE/DOUBLE/SUITE): ");
                                String type=scanner.nextLine();

                                break subRoomProgram;
                            case "time":
                                System.out.print("Nhập thời gian: ");
                                String date = scanner.nextLine();
                                DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
                                LocalDate time = LocalDate.parse(date, formatter);

                                break subRoomProgram;
                            default:
                                System.out.println("Nhập sai. Vui lòng nhập lại");
                                break;
                        }
                    }
                    break;
                case 0:

                    break roomProgram;
                default:
                    System.out.println("Nhập sai. Vui lòng nhập lại");
                    break;
            }
        } while (true);
    }
    //1. Hiển thị danh sách tất cả phòng
    public void getRoomList() {
        List<Room> rooms= roomRepository.getRooms();
        for(Room room : rooms) {
            System.out.println(room.toString());
        }
    }

    //2. Tra cứu phòng trống theo loại và thời gian lưu trú
    public Map<String, RoomSchedule> getCheckOutList(){
        Map<String, RoomSchedule> result= new HashMap<>();
        RoomScheduleRepository roomScheduleRepository = new RoomScheduleRepository();
        List<RoomSchedule> roomSchedules = roomScheduleRepository.getRoomSchedles();
        for(RoomSchedule roomSchedule : roomSchedules) {
            if(roomSchedule.geteRoomSchedule().toString().equalsIgnoreCase("checkout")) {
                result.put(roomSchedule.getRoom().getId(), roomSchedule);
            }
        }
        return result;
    }
    public Map<String, RoomSchedule> getCheckInList(){
        Map<String, RoomSchedule> result= new HashMap<>();
        RoomScheduleRepository roomScheduleRepository = new RoomScheduleRepository();
        List<RoomSchedule> roomSchedules = roomScheduleRepository.getRoomSchedles();
        for(RoomSchedule roomSchedule : roomSchedules) {
            if(roomSchedule.geteRoomSchedule().toString().equalsIgnoreCase("checkin")) {
                result.put(roomSchedule.getRoom().getId(), roomSchedule);
            }
        }
        return result;
    }

    public void findByRoomType(String type){

    }
}
