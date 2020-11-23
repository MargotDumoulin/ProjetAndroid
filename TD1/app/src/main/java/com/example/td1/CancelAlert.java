package com.example.td1;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AlertDialog;

public class CancelAlert extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Activity activity = getActivity();
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(activity, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(activity);
        }

        builder.setMessage(R.string.confirm_phrase)
                .setTitle(R.string.confirm_title);

        DialogInterface.OnClickListener listener = (DialogInterface.OnClickListener) activity;
        builder.setPositiveButton(R.string.confirm_yes, listener);
        builder.setNegativeButton(R.string.confirm_no, listener);

        return builder.create();
    }

}
