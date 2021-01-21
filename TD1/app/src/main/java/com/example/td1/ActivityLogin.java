package com.example.td1;

import com.example.td1.modele.Client;

public interface ActivityLogin {
    public void login();
    public void logout();
    public Client getLoggedInCustomer();
    public void updateLoggedInCustomer(Client customer);
}
