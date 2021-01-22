package com.example.td1.ui.editPersonalInfo;

import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.td1.ActivityLogin;
import com.example.td1.R;
import com.example.td1.modele.Client;
import com.example.td1.ui.register.RegisterFragment;

import at.favre.lib.crypto.bcrypt.BCrypt;

public class EditPersonalInfoFragment extends RegisterFragment {

    private Button saveButton;
    private EditText oldPasswordEditText;
    private EditText newPasswordEditText;
    private String customerPassword;

    @Override
    public void onStart() {
        super.onStart();

        Client customer = ((ActivityLogin) this.getActivity()).getLoggedInCustomer();
        this.customerPassword = customer.getPassword();

        // Init save btn and current password
        this.saveButton = this.root.findViewById(R.id.saveButton);

        this.oldPasswordEditText = this.root.findViewById(R.id.oldPasswordEditText);
        this.fields.add(new Pair(this.oldPasswordEditText, getString(R.string.oldPassword)));

        this.newPasswordEditText = this.root.findViewById(R.id.newPasswordEditText);
        this.fields.add(new Pair(this.newPasswordEditText, getString(R.string.newPassword)));

        // Change visibility for btns and password
        this.saveButton.setVisibility(View.VISIBLE);
        this.oldPasswordEditText.setVisibility(View.VISIBLE);
        this.newPasswordEditText.setVisibility(View.VISIBLE);

        this.registerButton.setVisibility(View.INVISIBLE);
        this.passwordEditText.setVisibility(View.INVISIBLE);

        // Fill the fields
        this.firstnameEditText.setText(customer.getFirstname());
        this.lastnameEditText.setText(customer.getLastname());
        this.identifierEditText.setText(customer.getIdentifier());
        this.addrNumberEditText.setText(String.valueOf(customer.getAddrNumber()));
        this.addrStreetEditText.setText(customer.getAddrStreet());
        this.addrPostalCodeEditText.setText(String.valueOf(customer.getAddrPostalCode()));
        this.addrCityEditText.setText(customer.getAddrCity());
        this.addrCountryEditText.setText(customer.getAddrCountry());

        this.saveButton.setOnClickListener(this::onClickSave);
    }

    private void onClickSave(View view) {
        String oldPassword = this.customerPassword;

        if (this.oldPasswordEditText.getText().toString().trim().isEmpty()) {
            Toast.makeText(this.getContext(), getString(R.string.empty_old_password), Toast.LENGTH_LONG).show();
        } else if (!(BCrypt.verifyer().verify(this.oldPasswordEditText.getText().toString().toCharArray(), this.customerPassword)).verified) {
            Toast.makeText(this.getContext(), getString(R.string.wrong_old_password), Toast.LENGTH_LONG).show();
        } else if (this.newPasswordEditText.getText().toString().trim().length() > 0) {
            if (!this.newPasswordEditText.getText().toString().trim().equals(this.confirmPasswordEditText.getText().toString().trim())) {
                Toast.makeText(this.getContext(), getString(R.string.passwords_does_not_match), Toast.LENGTH_LONG).show();
            }
        } else {
            for (int i = 0; i < this.fields.size(); i++) {
                Pair<EditText, String> field = this.fields.get(i);

                Log.e("Before Field", field.second);

                if (field.second.equals(getString(R.string.confirm))
                        || field.second.equals(getString(R.string.oldPassword))
                        || field.second.equals(getString(R.string.newPassword))
                        || field.second.equals(getString(R.string.password))
                ) {
                    this.fields.remove(i);
                    i--;
                }
            }

            for (Pair<EditText, String> field : this.fields) {
                Log.e("After Field", field.second);
            }

            this.validateFields();
        }
    }
}
