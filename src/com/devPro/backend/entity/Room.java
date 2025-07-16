package com.devPro.backend.entity;

import com.devPro.enums.ERoom;
import com.devPro.enums.StatusRoom;

public class Room {
    private String id;
    private ERoom type;
    private int price;
    private StatusRoom status;
    public Room() {

    }
    public Room(String id) {
        this.id=id;
    }
    public Room(String id, ERoom type, int price, StatusRoom status) {
        this.id = id;
        this.type = type;
        this.price = price;
        this.status = status;
    }
    public String getId() {
        return id;
    }
    public ERoom getType() {
        return type;
    }
    public int getPrice() {
        return price;
    }
    public StatusRoom getStatus() {
        return status;
    }
    public void setType(ERoom type) {
        this.type = type;
    }
    public void setPrice(int price) {
        this.price = price;
    }
    public void setStatus(StatusRoom status) {
        this.status = status;
    }
    public Room findByRoomType(String roomType) {
        if(roomType.equalsIgnoreCase(type.toString())) {
            return this;
        }
        return null;
    }
    @Override
    public String toString() {
        return "Department [id=" + id + ", type=" + type + ", price=" + price + ", status=" + status + "]";
    }
}
