package campus02.iwi2014.timeeey;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by michael on 29.05.17.
 */

public class OfflineDb{

    private DbHelper dbHelper;
    private SQLiteDatabase database;

    public final static String tableJsons="jsonStrings";
    public final static String jsonString="jsonString";

    public final static String tableTasks="tasks";
    public final static String taskId="id";
    public final static String taskName="name";
    public final static String taskDescription="description";

    public OfflineDb(Context context){
        dbHelper = new DbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long addJsonString(String json){
        ContentValues values = new ContentValues();
        values.put(jsonString, json);
        return database.insert(tableJsons, null, values);
    }

    public Cursor selectJsonStrings() {
        String[] cols = new String[] {jsonString};
        Cursor mCursor = database.query(true, tableJsons,cols,null
                , null, null, null, "id", null);
       /* if (mCursor != null) {
            mCursor.moveToFirst();
        }*/
        return mCursor; // iterate to get each value.
    }

    public long addTask(String id, String name, String description){
        ContentValues values = new ContentValues();
        values.put(taskId, id);
        values.put(taskName, name);
        values.put(taskDescription, description);
        return database.insert(tableTasks, null, values);
    }

    public Cursor selectTasks() {
        String[] cols = new String[] {taskId, taskName, taskDescription};
        Cursor mCursor = database.query(true, tableTasks,cols,null
                , null, null, null, taskId, null);
        /*if (mCursor != null) {
            mCursor.moveToFirst();
        }*/
        return mCursor; // iterate to get each value.
    }

    public Cursor selectTaskById(long id) {
        String[] cols = new String[] {taskId, taskName, taskDescription};
        Cursor mCursor = database.query(true, tableTasks,cols,taskId+"=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        /*if (mCursor != null) {
            mCursor.moveToFirst();
        }*/
        return mCursor; // iterate to get each value.
    }

    public void deleteTasks()
    {
        database.delete(tableTasks, null, null);
    }

    public void deleteJsons()
    {
        database.delete(tableJsons, null, null);
    }
}
