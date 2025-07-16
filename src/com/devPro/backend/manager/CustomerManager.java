package com.devPro.backend.manager;

import com.devPro.backend.entity.Customer;
import com.devPro.backend.repository.CustomerRepository;
import com.devPro.interfaces.ICustomer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CustomerManager implements ICustomer {
    private Scanner scanner;
    private CustomerRepository customerRepository;

    public CustomerManager() {
        scanner = new Scanner(System.in);
        customerRepository = new CustomerRepository();
    }
    public void loadMenu() {
        menu: do {
            System.out.println("========== MENU ==========");
            System.out.println("1. Thêm khách hàng mới");
            System.out.println("2. Hiển thị danh sách khách hàng");
            System.out.println("3. Tìm kiếm khách hàng theo mã hoặc tên");
            System.out.println("0. Thoát khỏi chức năng");
            System.out.println("=========================");
            System.out.print("Mời nhập: ");
            int opp= Integer.parseInt(scanner.nextLine());
            switch (opp) {
                case 1:
                    addCustomer();
                    break;
                case 2:
                    displayCustomerList();
                    break;
                case 3:
                    findByCustomerIdOrName();
                    break;
                case 0:
                    break menu;
                default:
                    System.out.println("Nhập sai lựa chọn. Vui lòng nhập lại!");
                    break;
            }
        } while (true);
    }

    //1. Thêm khách hàng mới
    public void addCustomer(){
        System.out.print("Nhập id: ");
        String id = scanner.nextLine();
        System.out.print("Nhập name: ");
        String name = scanner.nextLine();
        System.out.print("Nhập số điện thoại: ");
        String phone = scanner.nextLine();
        System.out.print("Nhập số căn cước công dân: ");
        String cccd = scanner.nextLine();

        String line = id + ";" + name + ";" + phone + ";" + cccd;

        File f = new File(fileAddressCustomer);
        BufferedWriter bw = null;
        try {
            boolean fileNotEmpty= f.exists() && f.length()>0;
            bw = new BufferedWriter(new FileWriter(f, true));
            if(fileNotEmpty)
                bw.newLine();
            bw.write(line);

            System.out.println("Thêm thành công");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //2. Hiển thị danh sách khách hàng
    public void displayCustomerList() {
        //reload
        customerRepository = new CustomerRepository();
        //new customer list
        List<Customer> customers= customerRepository.getCustomers();
        for(Customer customer : customers) {
            System.out.println(customer.toString());
        }
    }

    //3. Tìm kiếm khách hàng theo mã hoặc tên
    public void findByCustomerIdOrName() {
        // reload
        customerRepository = new CustomerRepository();
        // new customer list
        List<Customer> customers = customerRepository.getCustomers();
        customerProgram: while(true) {
            System.out.print("Nhập kiểu tra cứu khách hàng(id/name): ");
            String text = scanner.nextLine();
            switch (text) {
                case "id":
                    System.out.print("Nhâp id: ");
                    String code = scanner.nextLine();
                    getCustomerById(customers, code);
                    break customerProgram;
                case "name":
                    System.out.print("Nhâp name: ");
                    String name = scanner.nextLine();
                    getCustomerByName(customers, name);
                    break customerProgram;
                default:
                    System.out.println("Nhập sai. Vui lòng nhập lại!");
                    break;
            }
        }
    }
    public void getCustomerById(List<Customer> customers, String code) {
        boolean flag = false;
        for(Customer customer : customers) {
            if(customer.findByCustomerId(code)!=null) {
                System.out.println(customer.toString());
                flag = true;
                break;
            }
        }
        System.out.println(flag?"":"Không có khách hàng này");
    }
    public void getCustomerByName(List<Customer> customers, String name) {
        boolean flag=false;
        for(Customer customer : customers) {
            if(customer.findByCustomerName(name)!=null) {
                System.out.println(customer.toString());
                flag=true;
            }
        }
        if(!flag) {
            System.out.println("Không có khách hàng này");
        }
    }
}
