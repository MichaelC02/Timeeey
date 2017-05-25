package campus02.iwi2014.timeeey;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.apache.http.client.ClientProtocolException;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class TaskActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        Spinner spinnerTasks;
        TextView textView = (TextView)findViewById(R.id.textView);
        List<String> x = null;

        spinnerTasks = (Spinner)findViewById(R.id.spinnerTasks);

        try {
             x = new MyAsyncTask().execute("task").get();
            textView.setText(x.get(0));
        }catch(InterruptedException | ExecutionException e){
            System.out.print("fu");
        }




    }
}
