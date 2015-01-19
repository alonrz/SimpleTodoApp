package com.example.alonrz.simpletodo;

import android.content.Intent;
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


public class MainActivity extends ActionBarActivity implements MyAlertDialogFragment.DeleteAlertDialogListener {

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
//        Intent i = new Intent(this, EditItemActivity.class);
//        i.putExtra("text", items.get(position).toString());
//        i.putExtra("position", position);
//        startActivityForResult(i, 0);
        TodoItem item = items.get(position);
        FragmentManager fm = getSupportFragmentManager();
        EditItemDialog frag = EditItemDialog.newInstance(item);
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
                onEditItem(position);
            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK)
        {
            String itemText = data.getStringExtra("text");
            int position = data.getIntExtra("position", 0);

            TodoItem item = items.get(position);
            item.setText(itemText);

            writeUpdateItem(item);
            itemsAdapter.notifyDataSetChanged();
        }
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
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, "todo.txt");
//        try {
//            items = new ArrayList<>(FileUtils.readLines(todoFile));

        items =  new ArrayList<>( db.getAllTodoItems());

//        } catch (IOException e) {
//            items = new ArrayList<>();
//        }
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


//    private void writeItems()
//    {
//        File filesDir = getFilesDir();
//        File todoFile = new File(filesDir, "todo.txt");
//        try {
//            FileUtils.writeLines(todoFile, items);
//        }
//        catch(IOException e) {
//            e.printStackTrace();
//        }


//    }

}
