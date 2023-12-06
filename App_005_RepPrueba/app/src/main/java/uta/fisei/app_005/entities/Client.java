package uta.fisei.app_005.entities;

public class Client {
    private int code;
    private String name;
    private String lastName;
    private String phone;
    private double balance;

    public Client() {
    }

    public Client(int code, String name, String lastName, String phone, double balance) {
        this.code = code;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.balance = balance;
    }
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

}
