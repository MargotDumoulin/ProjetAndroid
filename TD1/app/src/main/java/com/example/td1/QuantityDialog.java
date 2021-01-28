package com.example.td1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


import androidx.fragment.app.DialogFragment;

public class QuantityDialog extends DialogFragment implements DialogInterface.OnClickListener {

    private EditText editQuantity;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.item_quantity, null);
        this.editQuantity = view.findViewById(R.id.editTextItemQuantity);


        // Build dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle(getString(R.string.quantity));

        builder.setPositiveButton(R.string.confirm_yes, (DialogInterface.OnClickListener) this);
        builder.setNegativeButton(R.string.confirm_no, (DialogInterface.OnClickListener) this);
        builder.setView(view);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int position) {
        String value = editQuantity.getText().toString();
        Log.e("Quantityy", value);
        MainActivity callingActivity = (MainActivity) this.getActivity();
        callingActivity.onUserSelectValue(value);
        dialog.dismiss();
    }
}
