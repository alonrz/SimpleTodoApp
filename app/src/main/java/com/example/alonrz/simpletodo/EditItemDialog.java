package com.example.alonrz.simpletodo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by alonrz on 1/18/15.
 */
public class EditItemDialog extends DialogFragment implements View.OnClickListener {

    public interface EditItemDialogListener
    {
        void onFinishEditListener(String newText);
    }

    private static EditItemDialog dialogFrag = null;
    private EditText txtEditTextView;
    //empty ctor - must for fragments
    public EditItemDialog() {}


    public static EditItemDialog newInstance(TodoItem item)
    {
        dialogFrag = new EditItemDialog();

        Bundle args = new Bundle();
        args.putString("text", item.getText());
        args.putInt("priority", item.getPriority());
        dialogFrag.setArguments(args);
        return dialogFrag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_item, container);

        txtEditTextView = (EditText) view.findViewById(R.id.txtEditItemFragment);
        String text = getArguments().getString("text");
        txtEditTextView.setText(text);
        txtEditTextView.requestFocus();
        Button btnOK = (Button)view.findViewById(R.id.btnOK);
        Button btnCancel = (Button)view.findViewById(R.id.btnCancel);
        btnOK.setOnClickListener(this);
        btnCancel.setOnClickListener(this);

        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        return view;
    }



    @Override
    public void onClick(View v) {
        EditItemDialogListener listener = (EditItemDialogListener)getActivity();

        if(((Button)v).getText().toString().equalsIgnoreCase("Confirm"))
        {

            listener.onFinishEditListener(txtEditTextView.getText().toString());
        }
        else {
            listener.onFinishEditListener(null);

        }
        getDialog().dismiss();
    }
}
