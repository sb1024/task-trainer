package com.suzie.byun.technicaproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.suzie.byun.technicaproject.HomeActivity;

/**
 * Created by suzie on 11/5/2016.
 */

public class TaskDatabaseHelper extends SQLiteOpenHelper {
    Context context;
    public TaskDatabaseHelper(Context context) {
        super(context, TaskContract.DB_NAME, null, TaskContract.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TaskContract.TaskEntry.TABLE + " ( " +
                TaskContract.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskContract.TaskEntry.COL_TASK_TITLE + " TEXT NOT NULL);";
                //, " +
                //TaskContract.TaskEntry.COL_TASK_DONE + " INTEGER);";

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
        //values.put(TaskContract.TaskEntry.COL_TASK_DONE, done);
        long id = db.insertWithOnConflict(TaskContract.TaskEntry.TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();

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
