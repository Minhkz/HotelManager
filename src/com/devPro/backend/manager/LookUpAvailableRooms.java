package com.devPro.backend.manager;

import com.devPro.backend.entity.Room;
import com.devPro.backend.entity.RoomSchedule;
import com.devPro.backend.repository.RoomRepository;
import com.devPro.backend.repository.RoomScheduleRepository;
import com.devPro.interfaces.IFormatDate;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class LookUpAvailableRooms implements IFormatDate {
    private RoomScheduleRepository roomScheduleRepository;
    private RoomRepository roomRepository;

    public LookUpAvailableRooms() {
        roomScheduleRepository = new RoomScheduleRepository();
    }
    public Map<String, List<RoomSchedule>> getBookingRoomList(){
        Map<String, List<RoomSchedule>> results = new HashMap<>();
        List<RoomSchedule> roomSchedules = roomScheduleRepository.getRoomSchedles();
        Set<String> roomNames = new HashSet<>();
        for (RoomSchedule roomSchedule : roomSchedules) {
            roomNames.add(roomSchedule.getRoom().getId());
        }
        for (String roomName : roomNames) {
            List<RoomSchedule> roomScheduleList= new ArrayList<>();
            for (RoomSchedule roomSchedule : roomSchedules) {
                if (roomName.equals(roomSchedule.getRoom().getId())) {
                    roomScheduleList.add(roomSchedule);
                }
            }
            results.put(roomName, roomScheduleList);
        }

        return results;
    }
    public List<String> lookUpAvailableRoom(LocalDate startDate, LocalDate endDate) {
        Map<String, List<RoomSchedule>> results = getBookingRoomList();
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, List<RoomSchedule>> entry : results.entrySet()) {
            List<RoomSchedule> roomSchedules = entry.getValue();
            boolean check = true;
            for (RoomSchedule roomSchedule : roomSchedules) {
                if(roomSchedule.geteRoomSchedule().toString().equalsIgnoreCase("checkin")){
                    if (!(endDate.isBefore(roomSchedule.getBookingHistory())||endDate.equals(roomSchedule.getBookingHistory()))) {
                        check = false;
                    }
                } else if (roomSchedule.geteRoomSchedule().toString().equalsIgnoreCase("checkout")) {
                    if (!(startDate.isBefore(roomSchedule.getBookingHistory())||startDate.equals(roomSchedule.getBookingHistory()))) {
                        check = false;
                    }
                }
            }
            if (!check) {
                result.add(entry.getKey());
            }
        }
        return result;

    }
    public void displayAvailableRooms(LocalDate startDate, LocalDate endDate) {
        List<Room> rooms = roomRepository.getRooms();
        List<String> roomNames = lookUpAvailableRoom(startDate, endDate);
        for(Room room : rooms) {
            if(roomNames.contains(room.getId())) {
                continue;
            }
            System.out.println(room.toString());
        }
    }
}
