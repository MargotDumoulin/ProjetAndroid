package com.example.td1.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.td1.ActivityLogin;
import com.example.td1.R;
import com.example.td1.modele.Client;

public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        final TextView textView = root.findViewById(R.id.text_home);

        if (((ActivityLogin) this.getActivity()).isLoggedIn()) {
            Client customer = ((ActivityLogin) this.getActivity()).getLoggedInCustomer();
            String params[] = new String[2];
            params[0] = customer.getLastname();
            params[1] = customer.getFirstname();
            textView.setText(String.format(getString(R.string.welcome_name), params[0], params[1]));
        } else {
            textView.setText(getString(R.string.welcome_not_logged_in));
        }

        return root;
    }
}