package com.example.td1;

import com.example.td1.modele.Client;

public interface ActivityLogin {
    void login();
    void logout();
    Client getLoggedInCustomer();
    void updateLoggedInCustomer(Client customer);
    void updateDrawerWithCustomerInfo(Client customer);
    void removeInfoFromDrawer();
    boolean isLoggedIn();
}
