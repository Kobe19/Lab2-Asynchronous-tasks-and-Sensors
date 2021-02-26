package danielguirol.tp3.flickrapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class AsyncBitmapDownloader extends AsyncTask<String, Void, Bitmap> {

    URL url = null;
    HttpURLConnection urlConnection = null;
    Bitmap bm = null;
    ImageView bmImage;

    public AsyncBitmapDownloader(ImageView bmImage) {

        this.bmImage = (ImageView ) bmImage;
    }


    @Override
    protected Bitmap doInBackground(String... strings) {

        try {
            url = new URL(strings[0]);
            Log.i("CIO", "Downloading " + url);
            urlConnection = (HttpURLConnection) url.openConnection(); // Open
            InputStream in = new BufferedInputStream(urlConnection.getInputStream()); // Stream
            bm = BitmapFactory.decodeStream(in);
            in.close();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bm;

    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {

        bmImage.setImageBitmap(bitmap);
    }
}




