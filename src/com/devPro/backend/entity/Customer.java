package com.devPro.backend.entity;

public class Customer {
    private String id;
    private String name;
    private String phone;
    private String cccd;
    public Customer() {

    }
    public Customer(String id) {
        this.id=id;
    }
    public Customer(String id, String name, String phone, String cccd) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.cccd = cccd;
    }
    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public String getCccd() {
        return cccd;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Customer findByCustomerId(String id) {
        if(this.id.equals(id)){
            return this;
        }
        return null;
    }
    public Customer findByCustomerName(String name) {
        if(this.name.equals(name)){
            return this;
        }
        return null;
    }
    @Override
    public String toString() {
        return "Custommer [id=" + id + ", name=" + name + ", phone=" + phone + ", cccd=" + cccd + "]";
    }
}
