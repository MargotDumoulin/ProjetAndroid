package com.example.td1.modele;

import java.util.ArrayList;

public class Client {

    private int id;
    private String firstname;
    private String lastname;
    private String identifier;
    private String password;
    private String addr_street;
    private int addr_postal_code;
    private String addr_city;
    private String addr_country;


    public Client(int id, String firstname, String lastname, String identifier, String password, String addr_street, int addr_postal_code, String addr_city, String addr_country) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.identifier = identifier;
        this.password = password;
        this.addr_street = addr_street;
        this.addr_country = addr_country;
        this.addr_postal_code = addr_postal_code;
        this.addr_city = addr_city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddr_street() {
        return addr_street;
    }

    public void setAddr_street(String addr_street) {
        this.addr_street = addr_street;
    }

    public int getAddr_postal_code() {
        return addr_postal_code;
    }

    public void setAddr_postal_code(int addr_postal_code) {
        this.addr_postal_code = addr_postal_code;
    }

    public String getAddr_city() {
        return addr_city;
    }

    public void setAddr_city(String addr_city) {
        this.addr_city = addr_city;
    }

    public String getAddr_country() {
        return addr_country;
    }

    public void setAddr_country(String addr_country) {
        this.addr_country = addr_country;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

}
