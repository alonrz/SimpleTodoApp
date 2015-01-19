package com.example.alonrz.simpletodo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class MyAlertDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    public interface DeleteAlertDialogListener
    {
        void onFinishAlertDialog(boolean isConfirmed);
    }

    public MyAlertDialogFragment() {
        // Empty constructor required for DialogFragment
    }

    public static MyAlertDialogFragment newInstance(String text) {
        MyAlertDialogFragment frag = new MyAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("text", text);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String text = getArguments().getString("text");
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Confirm Delete");
        alertDialogBuilder.setMessage("About to delete " + text + "!\nAre you sure?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Confirm", this);
        alertDialogBuilder.setNegativeButton("Cancel", this);

        return alertDialogBuilder.create();
    }


    @Override
    public void onClick(DialogInterface dialog, int which) {
        DeleteAlertDialogListener listener = (DeleteAlertDialogListener) getActivity();

        if(which == DialogInterface.BUTTON_POSITIVE)
        {
            listener.onFinishAlertDialog(true);
        }
        else
        {
            listener.onFinishAlertDialog(false);
        }

        dismiss();
    }
}
