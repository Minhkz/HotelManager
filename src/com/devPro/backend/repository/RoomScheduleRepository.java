package com.devPro.backend.repository;

import com.devPro.backend.entity.Room;
import com.devPro.backend.entity.RoomSchedule;
import com.devPro.enums.ERoomSchedule;
import com.devPro.interfaces.IRoomSchedule;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RoomScheduleRepository implements IRoomSchedule {
    private List<RoomSchedule> roomSchedles;

    public RoomScheduleRepository() {
        roomSchedles= mockDataInFile();
    }

    public List<RoomSchedule> getRoomSchedles() {
        return roomSchedles;
    }

    public List<RoomSchedule> mockDataInFile() {
        List<RoomSchedule> list= new ArrayList<RoomSchedule>();
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("dd-MM-yyyy");
        File f=null;
        BufferedReader br=null;
        try {
            f= new File(fileAddressRoomSchedule);
            br = new BufferedReader(new FileReader(f));
            String line;
            br.readLine();
            while((line=br.readLine())!= null) {
                String [] parts = line.split(";");
                Room room = new Room(parts[0]);
                LocalDate bookingHistory = LocalDate.parse(parts[1], formatter);
                ERoomSchedule eRoomSchedule = ERoomSchedule.valueOf(parts[2].toUpperCase());
                list.add(new RoomSchedule(room, bookingHistory, eRoomSchedule));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (br!=null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
