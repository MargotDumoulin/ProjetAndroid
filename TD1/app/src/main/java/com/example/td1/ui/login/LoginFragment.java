package com.example.td1.ui.login;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.td1.ActivityLogin;
import com.example.td1.DAO.CustomerDAO;
import com.example.td1.R;
import com.example.td1.modele.Client;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment implements com.android.volley.Response.Listener<JSONObject>, com.android.volley.Response.ErrorListener{

    private View root;

    private EditText identifierEditText;
    private EditText passwordEditText;

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
                // we're getting info regarding the user logged in
                Client customer = new Client(response.getInt("id_client"),
                        response.getString("prenom"),
                        response.getString("nom"),
                        response.getString("identifiant"),
                        response.getString("mot_de_passe"),
                        response.getString("adr_voie"),
                        response.getInt("adr_code_postal"),
                        response.getInt("adr_numero"),
                        response.getString("adr_ville"),
                        response.getString("adr_pays")
                );

                ((ActivityLogin) this.getActivity()).updateLoggedInCustomer(customer);
                ((ActivityLogin) this.getActivity()).updateDrawerWithCustomerInfo(customer);
                this.redirectLoggedInCustomer();
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e("CATCH", e.getMessage());
        }
    }

}