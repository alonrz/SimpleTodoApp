package com.example.alonrz.simpletodo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by alonrz on 1/17/15.
 */
public class TodoItemsAdapter extends ArrayAdapter<TodoItem> {

    public TodoItemsAdapter(Context context, ArrayList<TodoItem> items){
        super(context,0, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TodoItem item = getItem(position);
        if(convertView == null)
        {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_todo, parent, false);
        }
        TextView txtText = (TextView)convertView.findViewById(R.id.txtTodoText);
        TextView txtPriority = (TextView)convertView.findViewById(R.id.txtPriority);

        txtText.setText(item.getText());
        txtPriority.setText("Priority: " + item.getPriority());

        return convertView;
    }
}
