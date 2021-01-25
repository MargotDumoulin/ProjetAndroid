package com.example.td1.modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Order implements Serializable {

    private int id;
    private Date date;
    private ArrayList<OrderLine> lines;

    public Order(int id, Date date, ArrayList<OrderLine> lines) {
        this.date = date;
        this.id = id;
        this.lines =  lines;
    }

    public ArrayList<OrderLine> getLines() {
        return lines;
    }

    public void setLines(ArrayList<OrderLine> lines) {
        this.lines = lines;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int orderId) {
        this.id = orderId;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
