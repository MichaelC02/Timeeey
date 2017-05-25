package campus02.iwi2014.timeeey;

/**
 * Created by michael on 25.05.17.
 */

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class MyAsyncTask extends AsyncTask<String,Void,List<String>> {


    //public AsyncResponse delegate = null;

    /*public MyAsyncTask(AsyncResponse delegate) {
        this.delegate = delegate;
    }*/

    /*@Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }*/

    @Override
    protected List<String> doInBackground(String... params) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet("http://10.211.55.5:1582/api/" + params[0]);
            HttpResponse response = client.execute(request);
            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line;
            List<String> stringList = new ArrayList<>();

            line = rd.readLine();
            while (line != null)
            {
                stringList.add(line);
                line = rd.readLine();
            }

            return stringList;
        }
        catch(IOException e)
        {
            System.out.print(e.getMessage());
            return null;
        }


    }
}
