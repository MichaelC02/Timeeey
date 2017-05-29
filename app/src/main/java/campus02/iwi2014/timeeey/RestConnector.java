package campus02.iwi2014.timeeey;

import android.database.Cursor;
import android.os.AsyncTask;
import android.text.TextUtils;

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

public class RestConnector {

    static DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:'00'");
    static DateFormat df2 = new SimpleDateFormat("dd.MM.yyyy HH:mm");
    static DateFormat df3 = new SimpleDateFormat("HH:mm");

    public static JSONArray GetTasks() {
        try {
            String jsonString = new MyAsyncTask().execute("get","task").get();

            if (!jsonString.equals("timeout"))
            {
                return new JSONArray(jsonString);
            }

            return null;
        } catch (InterruptedException | ExecutionException | JSONException e) {
            System.out.print(e.getMessage());
            return null;
        }
    }

    public static List<String> GetTaskNames() {
        try {
            List<String> taskNames = new ArrayList<>();
            String jsonString = new MyAsyncTask().execute("get","task").get();
            OfflineDb offlineDb = new OfflineDb(MyApplication.getContext());

            if (jsonString.equals("timeout")) {
                Cursor offlineTasks = offlineDb.selectTasks();

                if (offlineTasks != null) {
                    try {
                        while (offlineTasks.moveToNext()) {
                            taskNames.add(offlineTasks.getString(offlineTasks.getColumnIndex("name")));
                        }
                    } finally {
                        offlineTasks.close();
                    }
                }
            }
            else
            {
                JSONArray arr = new JSONArray(jsonString);

                for (int i = 0; i < arr.length(); i++) {
                    taskNames.add(arr.getJSONObject(i).getString("name"));
                }
            }

            return taskNames;
        } catch (InterruptedException | ExecutionException | JSONException e) {
            System.out.print(e.getMessage());
            return null;
        }
    }

    public static String GetTaskName(String taskId)
    {
        try {
            String jsonString = new MyAsyncTask().execute("get","task/"+taskId).get();
            JSONArray arr = new JSONArray(jsonString);

            return arr.getJSONObject(0).getString("name");


        } catch (InterruptedException | ExecutionException | JSONException e) {
            System.out.print(e.getMessage());
            return null;
        }
    }

    public static String GetTaskDescription(long taskId)
    {
        try {
            String jsonString = new MyAsyncTask().execute("get","task/"+taskId).get();

            if (jsonString.equals("timeout")) {
                OfflineDb offlineDb = new OfflineDb(MyApplication.getContext());
                Cursor offlineTasks = offlineDb.selectTaskById(taskId);

                if (offlineTasks != null)
                {
                    try {
                        offlineTasks.moveToFirst();
                        return offlineTasks.getString(offlineTasks.getColumnIndex("description"));
                    } finally {
                        offlineTasks.close();
                    }
                }

                return "";
            }
            else
            {
                JSONArray arr = new JSONArray(jsonString);
                return arr.getJSONObject(0).getString("description");
            }

        } catch (InterruptedException | ExecutionException | JSONException e) {
            System.out.print(e.getMessage());
            return "";
        }
    }

    public static String GetOpenEntry()
    {
        try {


            AsyncTask getOpenEntry = new MyAsyncTask().execute("get", "openentry");
            if (!getOpenEntry.isCancelled()) {

                return getOpenEntry.get().toString();

            } else {
                return null;
            }
        }
        catch(InterruptedException | ExecutionException e)
        {
            System.out.print(e.getMessage());
            return null;
        }
    }

    public static void StopCurrentTask()
    {
        try
        {
            String now = df.format(Calendar.getInstance().getTime());
            String jsonString = GetOpenEntry();

            if (!TextUtils.isEmpty(jsonString)) {
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

    public static JSONArray GetEntries() {
        try {
            List<String> taskNames = new ArrayList<>();
            String jsonString = new MyAsyncTask().execute("get","entry").get();
            return new JSONArray(jsonString);
            
        } catch (InterruptedException | ExecutionException | JSONException e) {
            System.out.print(e.getMessage());
            return null;
        }
    }

    public static void uploadEntries()
    {
        OfflineDb offlineDb = new OfflineDb(MyApplication.getContext());
        Cursor jsons = offlineDb.selectJsonStrings();

        if(jsons!= null) {
            try {
                while (jsons.moveToNext()) {
                    String json = jsons.getString(jsons.getColumnIndex("jsonString"));
                    String jsonString = new MyAsyncTask().execute("post", "entry", json).get();
                }

                offlineDb.deleteJsons();
            }
            catch(InterruptedException | ExecutionException e)
            {
                System.out.print(e.getMessage());
            }
            finally {
                jsons.close();
            }
        }
    }
}
