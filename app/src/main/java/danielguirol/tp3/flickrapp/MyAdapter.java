package danielguirol.tp3.flickrapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;

import java.util.Vector;

public class MyAdapter extends BaseAdapter {

    //creating a vector that will contains list of link coming from the AsyncFlickrJSONDataForList
    Vector<String> Vector = new Vector<String>();

    //a variable context to access some application-specific ressources and classes
    Context context_;

    //A constructor to call in the main of the activity
    public MyAdapter(Context context) {

        this.context_ = context;
    }

    // Add method to add take the data into the vector
    public void add(String url){

        Vector.add(url);
    }

    // retourne le nombre d'objet présent dans notre liste
    @Override
    public int getCount() {
        return Vector.size();
    }

    // retourne un élément de notre liste en fonction de sa position
    @Override
    public Object getItem(int position) {
        return null;
    }

    // retourne l'id d'un élément de notre liste en fonction de sa position
    @Override
    public long getItemId(int position) {
        return 0;
    }

    //This getView will handle the display of the information from the vector in a selected view
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //  retrieving elements from the vector
        //  String vector = Vector.get(position);
        //      //Here we inflate a new view in which we will display the elements
        //      if (convertView == null) {
        //        convertView = LayoutInflater.from(context_).inflate(R.layout.showlayout, parent, false);
        //                  }
        //
        //      TextView txt1 = (TextView) convertView.findViewById(R.id.textView2);
        //      txt1.setText(vector);

        //Here we inflate to instantiate the contents of layout XML files into their corresponding View objects
        if (convertView == null) {
              convertView = LayoutInflater.from(context_).inflate(R.layout.bitmaplayout, parent, false);
              }

        // Linking the element with the variable
        ImageView imageView2 = convertView.findViewById(R.id.imageView2);

        // here we instantiate a queue in order to visualize the structure it also a way of organising them
        RequestQueue queue = MySingleton.getInstance(context_).getRequestQueue();

        // The method that will contain the response
        Response.Listener<Bitmap> rep_listener = response -> {
            imageView2.setImageBitmap(response);
        };

        String vector = Vector.get(position);
        ImageRequest imageRequest = new ImageRequest(vector, rep_listener, 0, 0, ImageView.ScaleType.CENTER_CROP,
                null, new Response.ErrorListener() {

            // here we check if there is no issue with the Request
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Something goes wrong");
            }
        });

        queue.add(imageRequest);

        return convertView;
    }
}
