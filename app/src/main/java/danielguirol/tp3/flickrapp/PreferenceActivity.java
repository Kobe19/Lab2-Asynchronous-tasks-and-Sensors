package danielguirol.tp3.flickrapp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.util.Log;

import java.net.URL;


public class PreferenceActivity extends android.preference.PreferenceActivity {
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
        Load_setting();
    }


    private void Load_setting(){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        ListPreference LP = (ListPreference) findPreference("TAG");

        String mytag = sp.getString("TAG","false");
        if("1".equals(mytag)){
            str = "https://www.flickr.com/services/feeds/photos_public.gne?tags=cats&format=json&nojsoncallback=1";
        }else if ("2".equals(mytag)){
            str = "https://www.flickr.com/services/feeds/photos_public.gne?tags=dogs&format=json&nojsoncallback=1";
        }else if ("3".equals(mytag)){
            str = "https://www.flickr.com/services/feeds/photos_public.gne?tags=cars&format=json&nojsoncallback=1";
        }else if ("4".equals(mytag)){
            str = "https://www.flickr.com/services/feeds/photos_public.gne?tags=foods&format=json&nojsoncallback=1";
        }

        LP.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preferences, Object Obj) {
                String items = (String) Obj;
                if(preferences.getKey().equals("TAG")){
                    switch (items){
                        case "1":

                            str = "https://www.flickr.com/services/feeds/photos_public.gne?tags=cats&format=json&nojsoncallback=1";
                            break;
                        case "2":
                            str = "https://www.flickr.com/services/feeds/photos_public.gne?tags=dogs&format=json&nojsoncallback=1";
                            break;
                        case "3":
                            str = "https://www.flickr.com/services/feeds/photos_public.gne?tags=cars&format=json&nojsoncallback=1";
                            break;
                        case "4":
                            str = "https://www.flickr.com/services/feeds/photos_public.gne?tags=foods&format=json&nojsoncallback=1";
                            break;
                    }
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        Load_setting();
        super.onResume();
    }
}
