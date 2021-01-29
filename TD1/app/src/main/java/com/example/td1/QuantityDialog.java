package com.example.td1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import androidx.fragment.app.DialogFragment;

public class QuantityDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private EditText editQuantity;
    private String dialogCaller;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_quantity, null);
        this.editQuantity = view.findViewById(R.id.editTextItemQuantity);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle(getString(R.string.quantity));

        builder.setPositiveButton(R.string.confirm_yes, (DialogInterface.OnClickListener) this);
        builder.setNegativeButton(R.string.confirm_no, (DialogInterface.OnClickListener) this);
        builder.setView(view);

        if (savedInstanState != null) {
            this.dialogCaller = savedInstanState.getString("dialogCaller");
        }

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int position) {
        String value = editQuantity.getText().toString();
        MainActivity callingActivity = (MainActivity) this.getActivity();
        dialog.dismiss();
        callingActivity.onUserSelectValue(value, this.dialogCaller);
    }

    public void setUpDialogCaller(String dialogCaller) {
        this.dialogCaller = dialogCaller;
    }

    // ------ ROTATION ---------------------------------
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
            outState.putString("dialogCaller", this.dialogCaller);
    }
}
