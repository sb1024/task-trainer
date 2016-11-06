package com.suzie.byun.technicaproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.suzie.byun.technicaproject.HomeActivity;

/**
 * Created by suzie on 11/5/2016.
 */

public class TaskDatabaseHelper extends SQLiteOpenHelper {
    final static String TAG = "TaskDatabaseHelper";
    Context context;
    public TaskDatabaseHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL" +
                //");";
                ", " +
                TaskContract.TaskEntry.COL_TASK_DONE + " INT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskContract.TaskEntry.TABLE);
        onCreate(db);
    }

    public long addTask(String taskName, int done) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskContract.TaskEntry.COL_TASK_TITLE, taskName);
        values.put(TaskContract.TaskEntry.COL_TASK_DONE, done);
        long id = db.insertWithOnConflict(TaskContract.TaskEntry.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        ((HomeActivity)context).updateUI();
        Log.d(TAG, "Add task: " + taskName + ". Done? " + done + " id: " + id);
        return id;
    }

    public long updateTask(String taskName, int newIsDone) {
        //SQLiteDatabase db = getWritableDatabase();
        //ContentValues newValues = new ContentValues();
        //newValues.put(TaskContract.TaskEntry.COL_TASK_TITLE, taskName);
        //newValues.put(TaskContract.TaskEntry.COL_TASK_DONE, -1*oldIsDOne);
        //long id = db.replace(TaskContract.TaskEntry.TABLE, null, values);
        //db.delete(TaskContract.TaskEntry.TABLE, taskName, null);
        deleteTask(taskName);
        long id = addTask(taskName, newIsDone);
        //db.close();

        Log.d(TAG, "Update task: " + taskName + ". Done? " + newIsDone + " id: " + id);
        return id;
    }

    //public void deleteTask(long id) {
    public void deleteTask(String task) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        db.delete(TaskContract.TaskEntry.TABLE,
                //TaskContract.TaskEntry._ID + " = ?",
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                //new String[]{Long.toString(id)});
                new String[]{task});
        ((HomeActivity)context).updateUI();
        db.close();
    }
}
