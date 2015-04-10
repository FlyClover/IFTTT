package com.mobvoi.ifttt;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.mobvoi.ifttt.action.Action;
import com.mobvoi.ifttt.trigger.Trigger;

import java.util.ArrayList;
import java.util.List;

/**
 * @author AnkaZhao<kei@mobvoi.com>
 * @date 2015年04月11日
 */
public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = "DBHelper";
    private static final String DATABASE_NAME = "ifttt.db";
    private static final Integer DATABASE_VERSION = 1;
    private static final String TABLE_RECIPE = "recipe";
    private static final String TABLE_TRIGGER = "trigger";
    private static final String TABLE_ACTION = "action";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_TRIGGER_ID = "trigger_id";
    private static final String COLUMN_ACTION_ID = "action_id";
    private static final String COLUMN_TYPE = "type";
    private static final String COLUMN_DESC = "desc";

    private static final String SQL_CREATE_RECIPE = String.format("create table if not exists %s" +
                    " (%s integer primary key AutoIncrement, %s integer not null, %s integer not null)",
            TABLE_RECIPE, COLUMN_ID, COLUMN_TRIGGER_ID, COLUMN_ACTION_ID);
    private static final String SQL_CREATE_TRIGGER = String.format("create table if not exists %s" +
                    " (%s integer primary key AutoIncrement, %s text not null, %s text not null)",
            TABLE_TRIGGER, COLUMN_ID, COLUMN_TYPE, COLUMN_DESC);
    private static final String SQL_CREATE_ACTION = String.format("create table if not exists %s" +
                    " (%s integer primary key AutoIncrement, %s texxt not null, %s text not null)",
            TABLE_ACTION, COLUMN_ID, COLUMN_TYPE, COLUMN_DESC);

    private static final String SQL_LAST_ID = "select LAST_INSERT_ROWID()";

    private static final String[] QUERY_RECIPE_COLUMNS = {COLUMN_TRIGGER_ID, COLUMN_ACTION_ID};

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_RECIPE);
        db.execSQL(SQL_CREATE_TRIGGER);
        db.execSQL(SQL_CREATE_ACTION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        destroyAll();
        onCreate(db);
    }

    public void destroyAll() {
        Log.w(TAG, "destroy all tables");
        getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TABLE_RECIPE);
        getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TABLE_ACTION);
        getWritableDatabase().execSQL("DROP TABLE IF EXISTS " + TABLE_TRIGGER);
    }

    public void createAll() {
        getWritableDatabase().execSQL(SQL_CREATE_RECIPE);
        getWritableDatabase().execSQL(SQL_CREATE_TRIGGER);
        getWritableDatabase().execSQL(SQL_CREATE_ACTION);
    }

    public void deleteAllRecipe() {
        getWritableDatabase().execSQL("delete from recipe");
    }

    public void addRecipe(int triggerId, int actionId) {
        getWritableDatabase().execSQL("insert into " + TABLE_RECIPE + " (trigger_id, action_id)" +
                " values (" + triggerId + ", " + actionId + ")");
    }

    public int addTrigger(Trigger.Model triggerModel) {
        ContentValues values = new ContentValues();
        values.put("type", triggerModel.type);
        values.put("desc", triggerModel.desc);
        getWritableDatabase().insert(TABLE_TRIGGER, null, values);
//        getWritableDatabase().execSQL("insert into " + TABLE_TRIGGER + " (type, desc) values " +
//                " ('" + triggerModel.type + "','" + triggerModel.desc + "')");
        return getInsertedId();
    }

    public int addAction(Action.Model actionModel) {
        ContentValues values = new ContentValues();
        values.put("type", actionModel.type);
        values.put("desc", actionModel.desc);
        getWritableDatabase().insert(TABLE_ACTION, null, values);
//        getWritableDatabase().execSQL("insert into " + TABLE_ACTION + " (type, desc) values " +
//                " ('" + actionModel.type + "','" + actionModel.desc + "')");
        return getInsertedId();
    }

    private int getInsertedId() {
        Cursor cursor = getWritableDatabase().rawQuery(SQL_LAST_ID, null);
        int insertedId = 0;
        cursor.moveToFirst();
        insertedId = cursor.getInt(0);
        cursor.close();
        return insertedId;
    }

    public Trigger.Model getTrigger(int triggerId) {
        Trigger.Model triggerModel = new Trigger.Model();
        Cursor cursor = getWritableDatabase().rawQuery("select type, desc from trigger where id=" + triggerId, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            triggerModel.type = cursor.getString(0);
            triggerModel.desc = cursor.getString(1);
        }
        cursor.close();
        return triggerModel;
    }

    public Action.Model getAction(int actionId) {
        Action.Model actionModel = new Action.Model();
        Cursor cursor = getWritableDatabase().rawQuery("select type, desc from action where id=" + actionId, null);
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            actionModel.type = cursor.getString(0);
            actionModel.desc = cursor.getString(1);
        }
        cursor.close();
        return actionModel;
    }

    public List<Recipe.Model> getRecipes() {
        List<Recipe.Model> recipes = new ArrayList<>();
        Cursor cursor = getWritableDatabase().query(TABLE_RECIPE, QUERY_RECIPE_COLUMNS, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Recipe.Model model = new Recipe.Model();
            model.trigger = getTrigger(cursor.getInt(0));
            model.action = getAction(cursor.getInt(1));
            recipes.add(model);
            cursor.moveToNext();
        }
        return recipes;
    }
}
