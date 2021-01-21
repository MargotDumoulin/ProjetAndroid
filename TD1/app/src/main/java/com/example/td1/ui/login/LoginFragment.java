package com.example.td1.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.td1.ActiviteECommerce;
import com.example.td1.ActivityLogin;
import com.example.td1.DAO.CustomerDAO;
import com.example.td1.R;
import com.example.td1.modele.Client;
import com.example.td1.utils.Triplet;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class LoginFragment extends Fragment implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener{

    private View root;

    private EditText identifierEditText;
    private EditText passwordEditText;

    private TextView accountNameTextView;
    private TextView accountIdentifierTextView;

    private Button loginBtn;
    private Button signInBtn;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        root = inflater.inflate(R.layout.fragment_login, container, false);

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();

        this.identifierEditText = this.root.findViewById(R.id.identifierLoginEditText);
        this.passwordEditText = this.root.findViewById(R.id.passwordLoginEditText);

        this.accountNameTextView = this.root.findViewById(R.id.accountNameTextView);
        this.accountIdentifierTextView = this.root.findViewById(R.id.accountIdentifierTextView);

        this.signInBtn = this.root.findViewById(R.id.signInButton);
        this.loginBtn = this.root.findViewById(R.id.loginButton);

        this.loginBtn.setOnClickListener(this::onClickLogin);
        this.signInBtn.setOnClickListener(this::onClickSignIn);

    }

    public void onClickSignIn(View v) {
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_register);
    }

    public void onClickLogin(View v) {
        if (TextUtils.isEmpty(this.identifierEditText.getText().toString()) || TextUtils.isEmpty(this.passwordEditText.getText().toString())) {
            Toast.makeText(this.getContext(), getString(R.string.must_fill_all_fields), Toast.LENGTH_SHORT).show();
        } else {
            JSONObject credentialsJsonObject = this.credentialsToJsonObject();
            CustomerDAO.isLoginInfoCorrect(this, credentialsJsonObject);
        }
    }

    public JSONObject credentialsToJsonObject() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("identifier", this.identifierEditText.getText().toString());
            jsonObject.put("password", this.passwordEditText.getText().toString());

            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return jsonObject;
        }
    }

    public void redirectLoggedInCustomer() {
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment);
        navController.navigate(R.id.nav_home);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.e("Volley error", String.valueOf(error));
        Toast.makeText(this.getContext(), R.string.error_db, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            if (response.has("isLoginInfoCorrect")) {
                // we're testing if the login info is correct
                if (response.getBoolean("isLoginInfoCorrect")) {
                    ((ActivityLogin) this.getActivity()).login();
                    CustomerDAO.getCustomerByIdentifier(this, this.identifierEditText.getText().toString());
                } else {
                    Toast.makeText(this.getContext(), getString(R.string.incorrect_login_info), Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e("test", response.getString("identifier"));
                // we're getting info regarding the user logged in
                Client customer = new Client(response.getInt("id"),
                        response.getString("firstname"),
                        response.getString("lastanme"),
                        response.getString("identifier"),
                        response.getString("password"),
                        response.getString("street"),
                        response.getInt("postalCode"),
                        response.getInt("number"),
                        response.getString("city"),
                        response.getString("country")
                );

                ((ActivityLogin) this.getActivity()).updateLoggedInCustomer(customer);
                this.accountIdentifierTextView.setText(customer.getIdentifier());
                this.accountNameTextView.setText(customer.getFirstname() + " " + customer.getLastname());
                this.redirectLoggedInCustomer();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}