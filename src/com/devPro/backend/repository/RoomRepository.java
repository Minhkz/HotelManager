package com.devPro.backend.repository;

import com.devPro.backend.entity.Room;
import com.devPro.enums.ERoom;
import com.devPro.enums.StatusRoom;
import com.devPro.interfaces.IRoom;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RoomRepository implements IRoom {
    private List<Room> rooms;

    public RoomRepository() {
        rooms= mockDataInFile();
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public List<Room> mockDataInFile() {
        List<Room> list= new ArrayList<Room>();
        File f= null;
        BufferedReader br= null;
        try {
            f= new File(fileAddressRoom);
            br = new BufferedReader(new FileReader(f));
            String line;
            br.readLine();
            while((line=br.readLine())!= null) {
                String [] parts = line.split(";");
                String id= parts[0];
                ERoom eRoom=ERoom.valueOf(parts[1].toUpperCase());
                int price =Integer.parseInt(parts[2]);
                StatusRoom statusRoom = StatusRoom.valueOf(parts[3].toUpperCase());
                list.add(new Room(id, eRoom, price, statusRoom));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (br != null) {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
