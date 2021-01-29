package com.example.td1;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import androidx.fragment.app.DialogFragment;

public class DeleteItemDialog extends DialogFragment implements DialogInterface.OnClickListener {


    @Override
    public Dialog onCreateDialog(Bundle savedInstanState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setTitle(getString(R.string.delete_article_from_basket));

        builder.setPositiveButton(R.string.confirm_yes, (DialogInterface.OnClickListener) this);
        builder.setNegativeButton(R.string.confirm_no, null);

        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int position) {
        MainActivity callingActivity = (MainActivity) this.getActivity();
        dialog.dismiss();
        callingActivity.onUserDeleteItemFromBasket();
    }
}
