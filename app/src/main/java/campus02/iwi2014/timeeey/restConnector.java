package campus02.iwi2014.timeeey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by michael on 26.05.17.
 */

public class restConnector {
    public static List<String> GetTasks() {
        try {
            List<String> taskNames = new ArrayList<>();
            String jsonString = new MyAsyncTask().execute("task").get();
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
            String jsonString = new MyAsyncTask().execute("task/"+taskId).get();
            JSONArray arr = new JSONArray(jsonString);

            return arr.getJSONObject(0).getString("description");


        } catch (InterruptedException | ExecutionException | JSONException e) {
            System.out.print(e.getMessage());
            return null;
        }
    }
}
