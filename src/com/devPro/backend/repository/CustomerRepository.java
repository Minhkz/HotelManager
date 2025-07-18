package com.devPro.backend.repository;

import com.devPro.backend.entity.Customer;
import com.devPro.interfaces.ICustomer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerRepository implements ICustomer {
    private List<Customer> customers;

    public CustomerRepository() {
        customers = mockDataInFile();
    }

    public List<Customer> getCustomers() {
        return customers;
    }


    public List<Customer> mockDataInFile() {
        List<Customer> list= new ArrayList<Customer>();
        BufferedReader br=null;
        File f= null;
        try {
            f= new File(fileAddressCustomer);
            br = new BufferedReader(new FileReader(f));
            String line;
            br.readLine();//bỏ đi dòng đầu trg file
            while((line=br.readLine())!= null) {
                String [] parts = line.split(";");
                String id= parts[0];
                String name= parts[1];
                String phone= parts[2];
                String cccd= parts[3];
                list.add(new Customer(id, name, phone, cccd));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return list;
    }
}
