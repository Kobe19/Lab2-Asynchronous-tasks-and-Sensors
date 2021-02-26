package danielguirol.tp3.flickrapp;

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
import java.util.concurrent.ExecutionException;

public class AsyncFlickrJSONData  extends AsyncTask<String, Void, JSONObject> {
    URL url = null;
    JSONObject jsonb;
    //String jObj1;
    String jObj2;
    private ImageView image;

    private AppCompatActivity myActivity;

    public AsyncFlickrJSONData(AppCompatActivity mainActivity) {

        myActivity = mainActivity;
    }


    @Override
    protected JSONObject doInBackground(String... strings) {
        try {

            String str ;
            // Open connexion to this url
            url = new URL("https://www.flickr.com/services/feeds/photos_public.gne?tags=cars&format=json&nojsoncallback=1");
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                //Get an inputstream to get the bytes and get a reader to to turn it into string
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(in);
                //just to test that all is working properly
                Log.i("JFL1","http working");

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

            JSONObject jObj = ja_data.getJSONObject(0);
            //Toast.makeText(this, jObj.getString("link"), Toast.LENGTH_LONG).show();

            //we retrieve the url of the image from the element media of the array and put it in a String variable
            JSONObject jObj3 = jObj.getJSONObject ("media");
            jObj2 = jObj3.getString("m");
            Log.i("JP",jObj2);
            image = myActivity.findViewById(R.id.imageView);
            String url = jObj2;

            //we just instantiate AsyncBitmapDownloader to download image coming from the JSON
            AsyncBitmapDownloader task2 = new AsyncBitmapDownloader(image);
            task2.execute(url);


        } catch (JSONException e) {
            e.printStackTrace();

        }

        super.onPostExecute(jsonb);

        //Log.i("JFL", String.valueOf(jsonb));
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
