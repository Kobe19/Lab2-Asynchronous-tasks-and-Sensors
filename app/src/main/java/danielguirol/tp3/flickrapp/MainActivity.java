package danielguirol.tp3.flickrapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // creating objects for the elements of my layouts
    private Button bttn1;
    private Button bttn2;
    private Button bttn3;
    public ImageView image;

    LocationManager locationManager;
    LocationListener locationListener;
    private final static int LOCATION_REQUEST_CODE = 1;

    /** Called when the activity is first created. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Linking each elements of the elements with the variable corresponding
        bttn1 = findViewById(R.id.bttn1);
        image = findViewById(R.id.image);
        bttn2 = findViewById(R.id.bttn2);
        bttn3 = findViewById(R.id.bttn3);

        bttn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        // ActionListener in order to give a command to the button
        bttn1.setOnClickListener(new GetImageOnclickListener());

        bttn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

        // Exercice 12 : the answer of the server is not really using the JSON format because Flickr is returning data in jsonp(JSON with Padding) format instead of json format.
        //                Should be removed the "jsonFlickrFeed(" at the top of the json document and the paranthesis at the end ")".
        // Exercice 13 : we choose such a type it improve background processing on an Android application without slowing down navigation and to
        //               update the application interface at the end of processing. Futhermore String is the type of the parameter sent at runtime and JSONObject the type of
        //               the execution of the result

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLatitude();
                Log.i("Latitude and Logitude", latitude + ", "+ longitude);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Toast.makeText(MainActivity.this,"Status changed!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                Toast.makeText(MainActivity.this,"Provider Enabled!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(MainActivity.this,"Provider Disabled!", Toast.LENGTH_SHORT).show();
            }
        };


        if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            //request user location update
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener);
        }else{
            //location permission
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION
                    }, LOCATION_REQUEST_CODE);
            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            if(ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                //request user location update
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0, locationListener);
            }
        }

    private class GetImageOnclickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            //Here i am executing my AsyncTask by creation an instance.
            //on the click the action will run
            new AsyncFlickrJSONData(MainActivity.this).execute();
        }
    }


}