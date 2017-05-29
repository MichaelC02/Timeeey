package campus02.iwi2014.timeeey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class OverviewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_overview);

        ListView taskList = (ListView)findViewById(R.id.listEntries);

        List<String> tasks = new ArrayList<>();
        JSONArray entries = RestConnector.GetEntries();

        try {
            for (int i = 0; i < entries.length(); i++) {
                String taskId = entries.getJSONObject(i).getString("taskID");
                String taskName = RestConnector.GetTaskName(taskId);
                String line;

                if (entries.getJSONObject(i).getString("timeOut").equals("0001-01-01T00:00:00")) {
                    line = "Seit " + RestConnector.df2.format(RestConnector.df.parse(entries.getJSONObject(i).getString("timeIn")))+": "+taskName;
                }
                else
                {
                   line= RestConnector.df2.format(RestConnector.df.parse(entries.getJSONObject(i).getString("timeIn"))) + " - " + RestConnector.df3.format(RestConnector.df.parse(entries.getJSONObject(i).getString("timeOut"))) + ": " + taskName;
                }

                tasks.add(line);
            }
        }
        catch(JSONException | ParseException e)
        {
            System.out.print(e.getMessage());
        }


        ArrayAdapter<String> entriesAdapter =
                new ArrayAdapter<String>(
                        this,
                        R.layout.overview_list_item,
                        R.id.overview_list_item_textview,
                        tasks);

        taskList.setAdapter(entriesAdapter);

    }
}
