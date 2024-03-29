package com.example.td1.modele;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Client implements Serializable {

    private int id;
    private int addrPostalCode;
    private int addrNumber;
    private String firstname;
    private String lastname;
    private String identifier;
    private String password;
    private String addrStreet;
    private String addrCity;
    private String addrCountry;

    public Client(int id, String firstname, String lastname, String identifier, String password, String addrStreet, int addrPostalCode, int addrNumber, String addrCity, String addrCountry) {
        this.id = id;
        this.addrNumber = addrNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.identifier = identifier;
        this.password = password;
        this.addrStreet = addrStreet;
        this.addrCountry = addrCountry;
        this.addrPostalCode = addrPostalCode;
        this.addrCity = addrCity;
    }

    public Client(String firstname, String lastname, String identifier, String password, String addrStreet, int addrPostalCode, int addrNumber, String addrCity, String addrCountry) {
        this.id = -1;
        this.addrNumber = addrNumber;
        this.firstname = firstname;
        this.lastname = lastname;
        this.identifier = identifier;
        this.password = password;
        this.addrStreet = addrStreet;
        this.addrCountry = addrCountry;
        this.addrPostalCode = addrPostalCode;
        this.addrCity = addrCity;
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

    public String getAddrStreet() {
        return addrStreet;
    }

    public void setAddrStreet(String addrStreet) {
        this.addrStreet = addrStreet;
    }

    public int getAddrPostalCode() {
        return addrPostalCode;
    }

    public void setAddrPostalCode(int addrPostalCode) {
        this.addrPostalCode = addrPostalCode;
    }

    public String getAddrCity() {
        return addrCity;
    }

    public void setAddrCity(String addrCity) {
        this.addrCity = addrCity;
    }

    public String getAddrCountry() {
        return addrCountry;
    }

    public void setAddrCountry(String addrCountry) {
        this.addrCountry = addrCountry;
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

    public int getAddrNumber() {
        return addrNumber;
    }

    public void setAddrNumber(int addrNumber) {
        this.addrNumber = addrNumber;
    }

    public String toJson() {
        JSONObject jsonObject= new JSONObject();
        try {
            jsonObject.put("id", getId());
            jsonObject.put("number", getAddrNumber());
            jsonObject.put("street", getAddrStreet());
            jsonObject.put("city", getAddrCity());
            jsonObject.put("country", getAddrCountry());
            jsonObject.put("postalCode", getAddrPostalCode());
            jsonObject.put("lastname", getLastname());
            jsonObject.put("firstname", getFirstname());
            jsonObject.put("identifier", getIdentifier());
            jsonObject.put("password", getPassword());

            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

}
