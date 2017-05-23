package campus02.iwi2014.timeeey;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    TextView txtName;
    TextView txtCompany;
    TextView txtDay;
    TextView txtDate;
    TextView txtDescription;

    String test = "";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        // Fixe Werte
        txtDate = (TextView) findViewById(R.id.txtDate);
        Timer t = new Timer();
        /*
        Funktioniert noch nicht

        t.schedule(new TimerTask() {
            @Override public void run() {
                DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
                Date currentDate = new Date();
                txtDate.setText(dateFormat.format(currentDate));
            }
        }, 0L, 1000L);*/
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date currentDate = new Date();
        txtDate.setText(dateFormat.format(currentDate));




        txtDay = (TextView)findViewById(R.id.txtDay);
        txtDay.setText(Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.GERMAN));


        // Dummy-Werte
        txtName = (TextView)findViewById(R.id.txtName);
        txtName.setText("Max Mustermann");

        txtCompany = (TextView)findViewById(R.id.txtCompany);
        txtCompany.setText("Mustermann GmbH");

        txtDescription = (TextView)findViewById(R.id.txtDescription);
        txtDescription.setText("Mustermann GmbH\nTest\nabc");

        txtDay.setTextColor(Color.BLACK);
        txtDate.setTextColor(Color.BLACK);
        txtName.setTextColor(Color.BLACK);
        txtCompany.setTextColor(Color.BLACK);
        txtDescription.setTextColor(Color.BLACK);
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
}
