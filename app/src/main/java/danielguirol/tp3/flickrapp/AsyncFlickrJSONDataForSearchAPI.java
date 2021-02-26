package danielguirol.tp3.flickrapp;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncFlickrJSONDataForSearchAPI extends AsyncTask<String, Void, JSONObject> {
    URL url = null;
    JSONObject jsonb;
    String jObj3;

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            // Open connexion to this url

            url = new URL("https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=4167810fbc47cc7f1da6d151edf224d5&has_geo=1&lat=47.081987&lon=2.415535&per_page=1&format=json");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                //Get an inputstream to get the bytes and get a reader to to turn it into string
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(in);

                //just to test that all is working properly
                Log.i("JFList","second http working");

                // Getting here all the object from the link in a JSON Format
                jsonb = new JSONObject(s);


            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return jsonb;
    }


    //Reading the stream and convert bytes into text
    private String readStream(InputStream in)  throws IOException{
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        in.close();
        String Jsonext = sb.substring("jsonFlickrApi(".length(),sb.length()-1);
        return Jsonext;
    }
}
