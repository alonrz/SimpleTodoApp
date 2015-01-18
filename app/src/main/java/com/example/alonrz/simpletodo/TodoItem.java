package com.example.alonrz.simpletodo;

/**
 * Created by alonrz on 1/16/15.
 */
public class TodoItem {
    private int id;
    private String text;
    private int priority;
    private boolean is_done; //0 = false, 1 = true.
    private String due_date;

    public TodoItem(String text, boolean is_done, int priority)
    {
        super();
        this.text = text;
        this.is_done = is_done;
        this.priority = priority;
    }

    public TodoItem(String text, boolean is_done, int priority, String due_date) {
        this(text, is_done, priority);
        this.due_date = due_date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean getIsDone() {
        return is_done;
    }

    public void setIsDone(boolean is_done) {
        this.is_done = is_done;
    }

    public String getDueDate() {
        return due_date;
    }

    public void setDueDate(String due_date) {
        this.due_date = due_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.getText().toString();
    }
}