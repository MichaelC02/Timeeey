package campus02.iwi2014.timeeey;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by michael on 26.05.17.
 */

public class restConnector {

    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:'00'");

    public static List<String> GetTasks() {
        try {
            List<String> taskNames = new ArrayList<>();
            String jsonString = new MyAsyncTask().execute("get","task").get();
            JSONArray arr = new JSONArray(jsonString);

            for (int i = 0; i < arr.length(); i++) {
                taskNames.add(arr.getJSONObject(i).getString("name"));
            }

            return taskNames;
        } catch (InterruptedException | ExecutionException | JSONException e) {
            System.out.print(e.getMessage());
            return null;
        }
    }

    public static String GetTaskDescription(long taskId)
    {
        try {
            String jsonString = new MyAsyncTask().execute("get","task/"+taskId).get();
            JSONArray arr = new JSONArray(jsonString);

            return arr.getJSONObject(0).getString("description");


        } catch (InterruptedException | ExecutionException | JSONException e) {
            System.out.print(e.getMessage());
            return null;
        }
    }

    public static void StopCurrentTask()
    {
        try
        {

            String now = df.format(Calendar.getInstance().getTime());
            String jsonString;

            AsyncTask getOpenEntry = new MyAsyncTask().execute("get","openentry");
            if (!getOpenEntry.isCancelled()) {

                jsonString = getOpenEntry.get().toString();
                JSONObject currentEntry = new JSONObject(jsonString);

                currentEntry.put("timeOut", now);
                jsonString = new MyAsyncTask().execute("post", "entry", currentEntry.toString()).get();
            }



        } catch (InterruptedException | ExecutionException | JSONException e) {
            System.out.print(e.getMessage());
        }
    }

    public static void StartTask(long taskId)
    {
        try
        {

            String now = df.format(Calendar.getInstance().getTime());
            StopCurrentTask();

            JSONObject newEntry = new JSONObject();
            newEntry.put("timeIn", now);
            newEntry.put("taskId", taskId);
            String jsonString = new MyAsyncTask().execute("post", "entry", newEntry.toString()).get();

        } catch (InterruptedException | ExecutionException | JSONException e) {
            System.out.print(e.getMessage());
        }
    }
}
