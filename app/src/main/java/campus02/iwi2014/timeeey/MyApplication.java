package campus02.iwi2014.timeeey;

import android.app.Application;
import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by michael on 29.05.17.
 */

public class MyApplication extends Application {

    private static MyApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        OfflineDb offlineDb = new OfflineDb(this);

        JSONArray tasks = RestConnector.GetTasks();

        if (tasks != null && tasks.length() > 0) {
            RestConnector.uploadEntries();

            offlineDb.deleteTasks();

            for (int i = 0; i < tasks.length(); i++) {
                try {
                    JSONObject task = tasks.getJSONObject(i);
                    offlineDb.addTask(task.getString("id"), task.getString("name"), task.getString("description"));
                } catch (JSONException e)

                {
                    System.out.print(e.getMessage());
                }
            }
        }
    }


    public static Context getContext()
    {
        return instance;
    }
}