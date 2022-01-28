package com.example.uburn2;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper  {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "uBurn";
    private static final String TABLE_GOALS = "goals";
    private static final String KEY_ID = "id";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_WEIGHT_GOAL = "goalWeight";
    private static final String KEY_WEIGHT_DATE = "goalDate";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        //3rd argument to be passed is CursorFactory instance
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_GOALS + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_WEIGHT + " TEXT,"
                + KEY_WEIGHT_GOAL + " TEXT," + KEY_WEIGHT_DATE + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GOALS);

        // Create tables again
        onCreate(db);
    }

    // code to add the new contact
    void addGoal(Goal goal) {
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

    // code to get the single goal
    Goal getContact(int id) {
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

    // Deleting single goal
    public void deleteGoal(Goal goal) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_GOALS, KEY_ID + " = ?",
                new String[] { String.valueOf(goal.getID()) });
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
}
