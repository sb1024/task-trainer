package com.suzie.byun.technicaproject;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.suzie.byun.technicaproject.database.TaskContract;
import com.suzie.byun.technicaproject.database.TaskDatabaseHelper;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    final static String TAG = "HomeActivity";
    FloatingActionButton addTaskButton;
    ListView todoListView;

    //ArrayList<String> taskNames = new ArrayList<>();
    //ArrayList<Long> taskIds = new ArrayList<>();
    ArrayList<Task> tasks = new ArrayList<>();
    private CheckListAdapter mAdapter;

    TaskDatabaseHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mHelper = new TaskDatabaseHelper(this);

        updateUI();

        todoListView = (ListView)findViewById(R.id.todo_list);
        String[] taskNameArray = new String[tasks.size()];
        for(int i = 0; i < tasks.size(); i++) {
            taskNameArray[i] = tasks.get(i).getName();
        }
        mAdapter = new CheckListAdapter(this, tasks.toArray(new Task[]{}));
        todoListView.setAdapter(mAdapter);

        // ListView Item Click Listener
        todoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                String itemValue = (String) todoListView.getItemAtPosition(position);

                // Show Alert
                Toast.makeText(getApplicationContext(), "Position :" + itemPosition
                        + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
                        .show();
            }
        });

        //updateUI();

        addTaskButton = (FloatingActionButton)findViewById(R.id.fab);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "You clicked the add button...", Snackbar.LENGTH_LONG)
                  //      .setAction("Action", null).show();
                Log.d(TAG, "add new task: launch dialog");

                final EditText taskEditText = new EditText(getApplicationContext());

                AlertDialog dialog = new AlertDialog.Builder(HomeActivity.this)
                        .setTitle("Add a new task")
                        .setMessage("Name the task:")

                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                task = task.trim();
                                long id = mHelper.addTask(task, -1);
                                Log.d(TAG, "Task added: " + task + ", db id: " + id);
                                tasks.add(new Task(task, false, id));
                                String[] taskNameArray = new String[tasks.size()];
                                todoListView.setAdapter(new CheckListAdapter(HomeActivity.this, tasks.toArray(new Task[]{})));

                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();

                dialog.setContentView(R.layout.dialog_add_task);
                dialog.show();
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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

    private class CheckListAdapter extends BaseAdapter {

        Context context;
        Task[] data;
        private LayoutInflater inflater = null;

        public CheckListAdapter(Context context, Task[] data) {
            // TODO Auto-generated constructor stub
            this.context = context;
            this.data = data;
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi = convertView;
            if (vi == null)
                vi = inflater.inflate(R.layout.item_todo, null);
            //CheckBox checkBox = (CheckBox) vi.findViewById(R.id.check_box);
            final EditText text = (EditText)vi.findViewById(R.id.task_label);
            text.setText(data[position].getName());
            text.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    Log.d(TAG, "new Title: " + ((EditText)v).getText().toString());
                    //data[position] = ((EditText)v).getText().toString();

                }
            });
            return vi;
        }
    }

    private void updateUI() {
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            Log.d(TAG, "Task: " + cursor.getString(idx));
            Log.d(TAG, "Position: " + cursor.getPosition());
            if(cursor.getString(idx) == null ) {
                tasks.remove(cursor.getPosition());
            }
            tasks.add(cursor.getPosition(), new Task(cursor.getString(idx), false, cursor.getPosition()));
        }

        if (mAdapter == null) {
            String[] taskNameArray = new String[tasks.size()];
            mAdapter = new CheckListAdapter(this, tasks.toArray(new Task[]{}));
        } else {
            mAdapter.notifyDataSetChanged();
        }
        cursor.close();
        db.close();
    }
}
