package campus02.iwi2014.timeeey;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView txtName;
    TextView txtCompany;
    TextView txtDay;
    TextView txtDate;
    TextView txtDescription;

    Button btnNewTask;
    Button btnOverview;

    ImageButton btnStop;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnNewTask = (Button)findViewById(R.id.btnNewTask);
        btnOverview = (Button)findViewById(R.id.btnOverview);
        btnStop = (ImageButton)findViewById(R.id.btnStop);

        // Fixe Werte
        txtDate = (TextView) findViewById(R.id.txtDate);

        txtDay = (TextView)findViewById(R.id.txtDay);
        txtDay.setText(Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.GERMAN));

        txtDescription = (TextView)findViewById(R.id.txtDescription);
        setDescription();

        // Dummy-Werte
        txtName = (TextView)findViewById(R.id.txtName);
        txtName.setText("Max Mustermann");

        txtCompany = (TextView)findViewById(R.id.txtCompany);
        txtCompany.setText("Mustermann GmbH");

        //Text-Farbe setzen
        txtDay.setTextColor(Color.BLACK);
        txtDate.setTextColor(Color.BLACK);
        txtName.setTextColor(Color.BLACK);
        txtCompany.setTextColor(Color.BLACK);


        // Buttons
        btnNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, TaskActivity.class));
            }
        });

        btnOverview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, OverviewActivity.class));
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                RestConnector.StopCurrentTask();

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("Tätigkeit wurde beendet!");
                alertDialogBuilder.setPositiveButton("OK", null);
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                setDescription();
                /*Intent intent = getIntent();
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                finish();
                startActivity(intent);*/
            }
        });

        // Thread um Datum und Uhrzeit aktuell zu halten
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                updateDateTime();
                            }
                        });
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateDateTime()
    {
        Date currentDate = new Date();
        txtDate.setText(RestConnector.df2.format(currentDate));
    }

    private void setDescription()
    {
        txtDescription.setTextColor(Color.BLACK);
        btnOverview.setEnabled(true);
        btnStop.setEnabled(true);
        String currentEntry = RestConnector.GetOpenEntry();
        if (currentEntry.equals("noContent")) {
            txtDescription.setText("Derzeit wird keine Tätigkeit ausgeführt.");
            btnStop.setEnabled(false);
        }
        else if(currentEntry.equals("timeout")) {
            txtDescription.setText("Es besteht derzeit keine Verbindung zum Server");
            txtDescription.setTextColor(Color.RED);
            btnOverview.setEnabled(false);
        }
        else
        {
            try {

                JSONObject currentTask = new JSONObject(currentEntry);
                String taskName= RestConnector.GetTaskName(currentTask.getString("taskID"));

                txtDescription.setText("Derzeit wird die Tätigkeit '"+taskName+"' ausgeführt.");
            }
            catch(JSONException e)
            {
                System.out.print(e.getMessage());
            }
        }
    }



}
