package campus02.iwi2014.timeeey;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.json.*;

public class TaskActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Spinner spinnerTasks;
        TextView txtDescription = (TextView)findViewById(R.id.txtDescription);
        txtDescription.setTextColor(Color.BLACK);
        List<String> tasks=restConnector.GetTasks();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, tasks);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerTasks = (Spinner)findViewById(R.id.spinnerTasks);
        spinnerTasks.setAdapter(adapter);
        spinnerTasks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String description=restConnector.GetTaskDescription(id+1);
                TextView txtDescription = (TextView)findViewById(R.id.txtDescription);
                txtDescription.setText(description);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });






    }
}
