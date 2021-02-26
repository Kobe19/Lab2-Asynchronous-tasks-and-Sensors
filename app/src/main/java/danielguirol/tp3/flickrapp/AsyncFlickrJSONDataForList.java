package danielguirol.tp3.flickrapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
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

public class AsyncFlickrJSONDataForList extends AsyncTask<String, Void, JSONObject> {
    URL url = null;
    JSONObject jsonb;
    String jObj3;
    private ImageView image;
    MyAdapter adapter;



    public AsyncFlickrJSONDataForList(MyAdapter adapter) {
        this.adapter = adapter;

    }

    @Override
    protected JSONObject doInBackground(String... strings) {
        try {
            // Open connexion to this url

            url = new URL("https://www.flickr.com/services/feeds/photos_public.gne?tags=cars&format=json&nojsoncallback=1");
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

    @Override
    protected void onPostExecute(JSONObject jsonb) {


        try {

            //extracting data array from json string
            JSONArray ja_data = jsonb.getJSONArray("items");
            int length = ja_data.length();

                for(int i=0; i<length; i++)
                {
                    JSONObject jObj = ja_data.getJSONObject(i);
                    JSONObject jObj2 = jObj.getJSONObject("media");
                    jObj3 = jObj2.getString ("m");
                    String url = jObj3;
                    adapter.add(url);
                    adapter.notifyDataSetChanged();
                    Log.i("JFL3", "Adding to adapter url : " + url);


                    //Log.i("JFL3", "insertion okay ");
                }

            //image = myActivity.findViewById(R.id.imageView);
            //String url = jObj2;
            //AsyncBitmapDownloader task2 = new AsyncBitmapDownloader(image);
            //task2.execute(url);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //Reading the stream and convert bytes into text
    private String readStream(InputStream in)  throws IOException{
        StringBuilder sb = new StringBuilder();
        BufferedReader r = new BufferedReader(new InputStreamReader(in),1000);
        for (String line = r.readLine(); line != null; line =r.readLine()){
            sb.append(line);
        }
        in.close();
        return sb.toString();
    }
}

