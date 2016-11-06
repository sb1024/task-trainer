package com.suzie.byun.technicaproject;

/**
 * Created by suzie on 11/6/2016.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.suzie.byun.technicaproject.database.TaskContract;
import com.suzie.byun.technicaproject.database.TaskDatabaseHelper;

import java.util.ArrayList;


public class RewardActivity extends AppCompatActivity {
    final static String TAG = "RewardActivity";
    ListView todoListView;
    Button nextPageButton;

    ArrayList<Task> tasks = new ArrayList<>();
    TaskDatabaseHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        mHelper = new TaskDatabaseHelper(this);

        Intent intent = getIntent();
        int finished = intent.getIntExtra(HomeActivity.EXTRA_FINISHED_TASKS, 0);
        int unfinished = intent.getIntExtra(HomeActivity.EXTRA_UNFINISHED_TASKS, 0);
        Log.d(TAG, "finished count: " + finished);
        Log.d(TAG, "unfinished count: " + unfinished);

        GridView gridview = (GridView) findViewById(R.id.gridview);
        ImageAdapter adapter = new ImageAdapter(this);
        adapter.setPlantIds(finished);

        gridview.setAdapter(adapter);

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


    private class ImageAdapter extends BaseAdapter {
        private Context mContext;

        public ImageAdapter(Context c) {
            mContext = c;
        }

        public int getCount() {
            return mPlantIds.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return 0;
        }

        // create a new ImageView for each item referenced by the Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                // if it's not recycled, initialize some attributes
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(250, 250));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setPadding(15, 15, 15, 15);
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageResource(mPlantIds[position]);
            return imageView;
        }

        // references to our images
        private Integer[] mPlantIds = {
                R.drawable.plant_1, R.drawable.plant_1, R.drawable.plant_1,
                R.drawable.plant_2, R.drawable.plant_2,
                R.drawable.plant_3
        };

        public void setPlantIds(int finished) {
            ArrayList<Integer> plantIds = new ArrayList<>();
            int three = finished/15;
            finished = finished%15;
            for(int i = 0; i<three; i++) {
                plantIds.add(R.drawable.plant_3);
            }
            int two = finished/10;
            finished = finished%10;
            for(int i = 0; i<two; i++) {
                plantIds.add(R.drawable.plant_2);
            }

            int one = finished;
            for(int i = 0; i<two; i++) {
                plantIds.add(R.drawable.plant_1);
            }

        }
    }
}
