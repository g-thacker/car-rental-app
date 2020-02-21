package edu.txstate.get26.carrentalapp;

import android.app.ListActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.message.BasicHeader;

public class MenuActivity extends ListActivity {

    List<Car> cars;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getCars();
    }


    void getCars(){
        List<Header> headers = new ArrayList<>();
        headers.add(new BasicHeader("Accept", "application/json"));

        RestApiClient.get(MenuActivity.this, "cars.json", headers.toArray(new Header[headers.size()]), null, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                // ArrayList of menu items
                cars = new ArrayList<Car>();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        cars.add(new Car(response.getJSONObject(i)));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                setListAdapter(new ArrayAdapter<>(MenuActivity.this, R.layout.activity_menu, R.id.txtList, cars));
//                CustomListViewAdaptor customListViewAdaptor = new CustomListViewAdaptor(MenuActivity.this, R.layout.activity_menu, cars);
//                setListAdapter(customListViewAdaptor);
            }
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                cars = new ArrayList<>();
                Iterator<String> keys = response.keys();
                while (keys.hasNext()) {
                    String key = keys.next();
                    try {
                        cars.add(new Car(response.getJSONObject(key)));
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                setListAdapter(new ArrayAdapter<>(MenuActivity.this, R.layout.activity_menu, R.id.txtList, cars));
//                CustomListViewAdaptor customListViewAdaptor = new CustomListViewAdaptor(MenuActivity.this, R.layout.activity_menu, cars);
//                setListAdapter(customListViewAdaptor);
            }
        });
    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {// position is the index

        Car selectedCar = cars.get(position);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(MenuActivity.this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        //pass car info along to next activity
        editor.putInt("id", selectedCar.getId());
        editor.putString("make", selectedCar.getMake());
        editor.putString("model", selectedCar.getModel());
        editor.putString("color", selectedCar.getColor());
        editor.putFloat("dailyrate", (float)selectedCar.getDailyRate());
        editor.putString("url", selectedCar.getUrl());
        editor.putInt("pos", position);
        editor.commit();

        startActivity(new Intent(MenuActivity.this, DetailActivity.class));


    }
}
