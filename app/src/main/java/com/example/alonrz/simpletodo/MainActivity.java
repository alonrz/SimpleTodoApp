package com.example.alonrz.simpletodo;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements MyAlertDialogFragment.DeleteAlertDialogListener, EditItemDialog.EditItemDialogListener {

    ArrayList<TodoItem> items;
    TodoItemsAdapter itemsAdapter;
    ListView lvItems;
    TodoItemDatabase db;
    TodoItem mSelectedItem;
    int mSelectedPosition = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvItems = (ListView)findViewById(R.id.lvItems);
        db = new TodoItemDatabase(this);
        readItems();
        itemsAdapter = new TodoItemsAdapter(this, items);

        lvItems.setAdapter(itemsAdapter);

        setupListViewListener();
    }

    public void onAddItem(View v)
    {
        EditText etNewItem = (EditText)findViewById(R.id.etNewItem);
        Spinner mySpinner = (Spinner)findViewById(R.id.spinnerPriority);
        String priorityText = mySpinner.getSelectedItem().toString();
        TodoItem item = new TodoItem(etNewItem.getText().toString(), false, Integer.parseInt(priorityText));
        int id = writeNewItem(item);
        item.setId(id);

        itemsAdapter.add(item);

        etNewItem.setText("");
    }

    private void onEditItem(int position) {
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialog frag = EditItemDialog.newInstance(mSelectedItem);
        frag.show(fm, "fragment_edit_item");
    }

    private void setupListViewListener()
    {
        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedPosition = position;
                TodoItem item = items.get(mSelectedPosition);
                if(item !=null) {
                    mSelectedItem = item;
                    MyAlertDialogFragment frag = MyAlertDialogFragment.newInstance(item.getText());
                    frag.show(getSupportFragmentManager(), "delete_warning");
                }
                else
                    return false;

                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedPosition = position;
                TodoItem item = items.get(mSelectedPosition);
                if(item !=null) {
                    mSelectedItem = item;
                    onEditItem(mSelectedPosition);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void readItems()
    {

        items =  new ArrayList<>( db.getAllTodoItems());
    }

    private int writeNewItem(TodoItem item)
    {
        return (int)db.addTodoItem(item);
    }

    private void writeUpdateItem(TodoItem item)
    {
        db.updateTodoItem(item);
    }

    @Override
    public void onFinishAlertDialog(boolean isConfirmed) {
        if(isConfirmed)
        {
            db.deleteTodoItem(mSelectedItem);
            items.remove(mSelectedPosition);
            itemsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onFinishEditListener(String newText) {
        if(newText == null)
            return; //Cancel was pressed.
        mSelectedItem.setText(newText);


        mSelectedItem.setText(newText);

        writeUpdateItem(mSelectedItem);
        itemsAdapter.notifyDataSetChanged();

    }
}
