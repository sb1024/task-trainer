package com.suzie.byun.technicaproject;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {
    final static String TAG = "HomeActivity";
    FloatingActionButton addTaskButton;
    ListView todoListView;
    ImageButton deleteButton;
    CheckBox checkBox;

    ArrayList<String> taskNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        todoListView = (ListView)findViewById(R.id.todo_list);
        String[] taskNameArray = new String[taskNames.size()];
        todoListView.setAdapter(new CheckListAdapter(this, taskNames.toArray(taskNameArray)));

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
                                Log.d(TAG, "Task to add: " + task);
                                taskNames.add(task);
                                String[] taskNameArray = new String[taskNames.size()];
                                todoListView.setAdapter(new CheckListAdapter(HomeActivity.this, taskNames.toArray(taskNameArray)));

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
        String[] data;
        private LayoutInflater inflater = null;

        public CheckListAdapter(Context context, String[] data) {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View vi = convertView;
            if (vi == null)
                vi = inflater.inflate(R.layout.item_todo, null);
            //CheckBox checkBox = (CheckBox) vi.findViewById(R.id.check_box);
            EditText text = (EditText)vi.findViewById(R.id.task_label);
            text.setText(data[position]);
            return vi;
        }
    }
}
