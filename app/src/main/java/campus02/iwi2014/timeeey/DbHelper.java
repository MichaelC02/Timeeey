package campus02.iwi2014.timeeey;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by michael on 29.05.17.
 */

public class DbHelper extends SQLiteOpenHelper
{

    private static final String DATABASE_NAME = "timeeeyDb";
    private static final int DATABASE_VERSION = 2;

    // Database creation sql statement
    private static final String TABLE_JSONS_CREATE = "create table jsonStrings" +
            "(id integer primary key AUTOINCREMENT NOT NULL, jsonString text not null, postMethod varchar2(5) not null);";
    private static final String TABLE_TASKS_CREATE = "create table tasks" +
            "(id integer, name varchar2(100), description varchar2(100));";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database)
    {
        database.execSQL(TABLE_JSONS_CREATE);
        database.execSQL(TABLE_TASKS_CREATE);
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
        Log.w(DbHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS jsonStrings");
        database.execSQL("DROP TABLE IF EXISTS tasks");
        onCreate(database);
    }
}