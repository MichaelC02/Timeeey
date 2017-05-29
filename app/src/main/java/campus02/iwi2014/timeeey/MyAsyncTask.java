package campus02.iwi2014.timeeey;

/**
 * Created by michael on 25.05.17.
 */

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.HttpStatus;


public class MyAsyncTask extends AsyncTask<String,Void,String> {

    @Override
    // 1. Parameter = get/post
    // 2. Parameter = API-Methode
    // 3. Parameter = Post-Body
    protected String doInBackground(String... params) {
        try {
            HttpClient client = new DefaultHttpClient();
            HttpResponse response;

            if(params[0] == "get")
            {
                HttpUriRequest request = new HttpGet("http://10.211.55.5:1582/api/" + params[1]);
                response = client.execute(request);
            }
            else if(params[0] == "post")
            {
                HttpPost request = new HttpPost("http://10.211.55.5:1582/api/" + params[1]);
                request.addHeader("content-type", "text/json");
                HttpEntity entity = new ByteArrayEntity(params[2].getBytes("UTF-8"));
                request.setEntity(entity);
                response = client.execute(request);
            }
            else
            {
                return "";
            }

            if(response.getStatusLine().getStatusCode() == HttpURLConnection.HTTP_NO_CONTENT) {
                // Kein Content erhalten
                return "";
            }

            BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

            String line;
            //List<String> stringList = new ArrayList<>();

            line = rd.readLine();
            /*while (line != null)
            {
                stringList.add(line);
                line = rd.readLine();
            }
*/
            return line;
        }
        catch(IOException e)
        {
            System.out.print(e.getMessage());
            return "";
        }


    }
}
