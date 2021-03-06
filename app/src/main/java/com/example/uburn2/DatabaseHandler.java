package com.example.uburn2;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper  {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "uBurn";
    private static final String TABLE_GOALS = "goals";
    private static final String TABLE_WEIGHT = "weight";
    private static final String KEY_ID = "id";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_DATE = "date";
    private static final String KEY_WEIGHT_GOAL = "goalWeight";
    private static final String KEY_WEIGHT_DATE = "goalDate";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    public void CreateTables(SQLiteDatabase db) {
        ///only if missing...
        String CREATE_WEIGHT_TABLE = "CREATE TABLE " + TABLE_WEIGHT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_WEIGHT + " TEXT,"
                + KEY_DATE + " TEXT" + ")";

        db.execSQL(CREATE_WEIGHT_TABLE);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_GOALS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_WEIGHT + " TEXT,"
                + KEY_WEIGHT_GOAL + " TEXT," + KEY_WEIGHT_DATE + " TEXT" + ")";

        String CREATE_WEIGHT_TABLE = "CREATE TABLE " + TABLE_WEIGHT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_WEIGHT + " LONG,"
                + KEY_DATE + " DATE" + ")";

        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_WEIGHT_TABLE);

    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOALS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_WEIGHT);
        // Create tables again
        onCreate(db);
    }

    public void deleteDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        //db.delete(TABLE_GOALS, null, null);
        //db.delete(TABLE_WEIGHT, null, null);
        db.close();
    }

    public void emptyDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DELETE FROM " + TABLE_GOALS);
        db.execSQL("DELETE FROM " + TABLE_WEIGHT);
        db.close();
    }

    // code to add the new contact
    public void addGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WEIGHT, goal.getWeight());
        values.put(KEY_WEIGHT_GOAL, goal.getGoalWeight());
        values.put(KEY_WEIGHT_DATE, goal.getGoalDate());

        // Inserting Row
        db.insert(TABLE_GOALS, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to add the new contact
    public void addWeight(Weight weight) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WEIGHT, weight.getWeight());
        values.put(KEY_DATE, (int)(weight.getWeightDate().getTime()/1000));

        // Inserting Row
        db.insert(TABLE_WEIGHT, null, values);
        //2nd argument is String containing nullColumnHack
        db.close(); // Closing database connection
    }

    // code to get the single goal
    public Goal getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_GOALS, new String[] { KEY_ID,
                        KEY_WEIGHT, KEY_WEIGHT_GOAL, KEY_WEIGHT_DATE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Goal goal = new Goal(
                Integer.parseInt(cursor.getString(0)),
                Double.parseDouble(cursor.getString(1)),
                Double.parseDouble(cursor.getString(2)),
                cursor.getString(3)
        );
        // return contact
        return goal;
    }

    // code to get the single goal
    public Weight getWeight(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_WEIGHT, new String[] { KEY_ID,
                        KEY_WEIGHT, KEY_WEIGHT_DATE }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, KEY_WEIGHT_DATE, null);


        if (cursor != null) {
            cursor.moveToFirst();

            Weight weight = new Weight(
                    Integer.parseInt(cursor.getString(0)),
                    Double.parseDouble(cursor.getString(2)),
                    new Date(cursor.getLong(3)*1000)
            );

            Log.d("Error", cursor.getString(0));
            // return contact
            return weight;
        } else {
            return null;
        }
            }

    // code to get all goals in a list view
    public List<Goal> getAllGoals() {
        List<Goal> goalList = new ArrayList<Goal>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_GOALS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Goal goal = new Goal();
                goal.setID(Integer.parseInt(cursor.getString(0)));
                goal.setWeight(cursor.getDouble(1));
                goal.setGoalWeight(cursor.getDouble(2));
                goal.setGoalDate(cursor.getString(3));
                // Adding contact to list
                goalList.add(goal);
            } while (cursor.moveToNext());
        }

        // return contact list
        return goalList;
    }

    // code to get all weights in a list view
    public List<Weight> getAllWeights(String sortValue) {
        List<Weight> weightList = new ArrayList<Weight>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_WEIGHT + " Order By " + KEY_DATE + " " + sortValue;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Weight weight = new Weight();
                weight.setID(Integer.parseInt(cursor.getString(0)));
                weight.setWeight(cursor.getDouble(1));
                //weight.setWeightDate(cursor.getLong(2));
                weight.setWeightDate(new Date((cursor.getLong(2) *1000)));
                // Adding contact to list
                weightList.add(weight);
            } while (cursor.moveToNext());
        }

        // return contact list
        return weightList;
    }

    // code to update the single contact
    public int updateContact(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WEIGHT, goal.getWeight());
        values.put(KEY_WEIGHT_GOAL, goal.getGoalWeight());
        values.put(KEY_WEIGHT_DATE, goal.getGoalDate());

        // updating row
        return db.update(TABLE_GOALS, values, KEY_ID + " = ?",
                new String[] { String.valueOf(goal.getID()) });
    }

    // code to update the single weight
    public int updateWeight(Weight weight) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_WEIGHT, weight.getWeight());
        values.put(KEY_WEIGHT_DATE, (int)(weight.getWeightDate().getTime()/1000));

        // updating row
        return db.update(TABLE_WEIGHT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(weight.getID()) });
    }

    // Deleting single goal
    public void deleteGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GOALS, KEY_ID + " = ?",
                new String[] { String.valueOf(goal.getID()) });
        db.close();
    }

    // Deleting single weight
    public void deleteWeight(Weight weight) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_WEIGHT, KEY_ID + " = ?",
                new String[] { String.valueOf(weight.getID()) });
        db.close();
    }


    // Getting contacts Goal
    public int getGoalCount() {
        String countQuery = "SELECT  * FROM " + TABLE_GOALS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    // Getting contacts Goal
    public int getWeightCount() {
        String countQuery = "SELECT * FROM " + TABLE_WEIGHT;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public double getNewestWeight() {
        List<Weight> weightList = new ArrayList<Weight>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_WEIGHT + " ORDER BY " + KEY_DATE + " ASC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();

        Weight returnWeight = new Weight();
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Weight weight = new Weight();
                weight.setID(Integer.parseInt(cursor.getString(0)));
                weight.setWeight(cursor.getDouble(1));
                //weight.setWeightDate(cursor.getLong(2));
                weight.setWeightDate(new Date((cursor.getLong(2) *1000)));
                // Adding contact to list
                returnWeight = weight;
                weightList.add(weight);
            } while (cursor.moveToNext());
        }

        try {
            //
            //Weight max = Collections.max(weightList);
            return returnWeight.getWeight();
        } catch (Exception ex) {
            Log.d("DBERROR", ex.toString());
        }

        // return contact list
        return 0.00;

    }

    public double getWeight() {
        return getNewestWeight();
    }

    public double getBMI(Context ct) {

        try {
            SharedPreferences settings = ct.getSharedPreferences("UserInfo", 0);
            float value = Float.valueOf(settings.getString("Height", "").toString());

            double bmi = 0.0;

            bmi = (getWeight() / (value*value)) * 703;
            Log.d("BMI", "" + bmi);
            Log.d("BMI Calculation: ", "BMI: " + getWeight() + " Value: " + value + " English Convert val " + 703);
            bmi = Double.parseDouble(String.format("%.2f", bmi));
            return bmi;
        } catch (Exception ex){
            return 0.0;
        }

    }

    public double getAvgWeeklyWeightLoss() {
        return 0.0;
    }

    public double getWeightLossToDate() {
        return 0.0;
    }
}
