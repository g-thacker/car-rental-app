package edu.txstate.get26.carrentalapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.TextHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

public class UpdateActivity extends AppCompatActivity {

    int id;
    String make;
    String model;
    String color; // not used in this version but included for potential future use
    double dailyRate;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        id = sharedPreferences.getInt("id", 0);
        make = sharedPreferences.getString("make", "");
        model = sharedPreferences.getString("model", "");
        color = sharedPreferences.getString("color", "");
        dailyRate = sharedPreferences.getFloat("dailyrate", 0);
        position = sharedPreferences.getInt("pos", 0);

        TextView txtId = findViewById(R.id.txtId);
        TextView txtMake = findViewById(R.id.txtMake);
        TextView txtModel = findViewById(R.id.txtModel);
        final EditText txtNewRate = findViewById(R.id.txtNewRate);
        Button btnUpdate = findViewById(R.id.btnUpdate);
        Button btnHome = findViewById(R.id.btnHome);

        txtId.setText(id);
        txtMake.setText(make);
        txtModel.setText(model);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "cars/" + position + "/DailyRate.json";
                StringEntity entity = null;
                try {
                    double newRate = Double.parseDouble(txtNewRate.getText().toString());
                    entity = new StringEntity("" + newRate);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                entity.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/text"));
                RestApiClient.put(UpdateActivity.this, url, entity, "application/text", new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(UpdateActivity.this, "Error: " + responseString, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        Toast.makeText(UpdateActivity.this, "Update successful", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateActivity.this, MainActivity.class));
            }
        });
    }
}
