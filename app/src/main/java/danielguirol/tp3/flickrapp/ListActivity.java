package danielguirol.tp3.flickrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.Vector;

public class ListActivity extends AppCompatActivity {

    private ListView list;
    MyAdapter adapter;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        list = (ListView) findViewById(R.id.list);

        //Here it's a way of retrieving data in the myadapter from the getView and set in the list of our layout
        adapter = new MyAdapter(ListActivity.this);
        list.setAdapter(adapter);

        //Of course we need to execute the AsyncTask to get the data to treat and from the JSON URL
        new AsyncFlickrJSONDataForList(adapter).execute();
        Log.i("JFL", "Run successfully");


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.menu1:
                Intent i = new Intent(this,PreferenceActivity.class);
                startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }
}