package com.example.td1.ui.editPersonalInfo;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.td1.R;
import com.example.td1.ui.register.RegisterFragment;

public class EditPersonalInfoFragment extends RegisterFragment {

    private Button saveButton;
    private EditText oldPasswordEditText;

    @Override
    public void onStart() {
        super.onStart();

        this.saveButton = this.root.findViewById(R.id.saveButton);
        this.oldPasswordEditText = this.root.findViewById(R.id.oldPasswordEditText);

        this.saveButton.setVisibility(View.VISIBLE);
        this.oldPasswordEditText.setVisibility(View.VISIBLE);

        this.registerButton.setVisibility(View.INVISIBLE);
        this.passwordEditText.setVisibility(View.INVISIBLE);
    }
}
