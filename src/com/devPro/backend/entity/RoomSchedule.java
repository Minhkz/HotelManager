package com.devPro.backend.entity;

import com.devPro.enums.ERoomSchedule;

import java.time.LocalDate;

public class RoomSchedule {
    private Room room;
    private LocalDate bookingHistory;
    private ERoomSchedule eRoomSchedule;
    public RoomSchedule() {

    }

    public RoomSchedule(Room room, LocalDate bookingHistory, ERoomSchedule eRoomSchedule) {
        this.room = room;
        this.bookingHistory = bookingHistory;
        this.eRoomSchedule = eRoomSchedule;
    }
    public Room getRoom() {
        return room;
    }
    public LocalDate getBookingHistory() {
        return bookingHistory;
    }
    public ERoomSchedule geteRoomSchedule() {
        return eRoomSchedule;
    }
    public RoomSchedule findByRoomId(String code) {
        if(code.equals(room.getId())) {
            return this;
        }
        return null;
    }

    @Override
    public String toString() {
        return "RoomSchedule [room=" + room + ", bookingHistory=" + bookingHistory + ", eRoomSchedule=" + eRoomSchedule
                + "]";
    }
}
