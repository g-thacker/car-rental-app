package edu.txstate.get26.carrentalapp;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import cz.msebera.android.httpclient.entity.StringEntity;

public class DetailActivity extends AppCompatActivity {

    int id;
    String make;
    String model;
    String color;
    double dailyRate;
    String url;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        id = sharedPreferences.getInt("id", 0);
        make = sharedPreferences.getString("make", "");
        model = sharedPreferences.getString("model", "");
        color = sharedPreferences.getString("color", "");
        dailyRate = sharedPreferences.getFloat("dailyrate", 0);
        url = sharedPreferences.getString("url", "");
        position = sharedPreferences.getInt("pos", 0);

        final DecimalFormat dollar = new DecimalFormat("$##,###.00");

        TextView txtId = findViewById(R.id.txtId);
        TextView txtMake = findViewById(R.id.txtMake);
        TextView txtModel = findViewById(R.id.txtModel);
        TextView txtColor = findViewById(R.id.txtColor);
        TextView txtDailyRate = findViewById(R.id.txtDailyRate);

        txtId.setText(String.valueOf(id));
        txtMake.setText(make);
        txtModel.setText(model);
        txtColor.setText(color);
        txtDailyRate.setText(dollar.format(dailyRate) + "/day");

        Button calculate = findViewById(R.id.btnCalculate);
        final Button goToWeb = findViewById(R.id.btnWeb);
        Button updateRate = findViewById(R.id.btnUpdate);


        if (url.equals("")) {
            goToWeb.setEnabled(false);
        }
        goToWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goToWeb.isEnabled()) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            EditText daysRenting = findViewById(R.id.txtDays);
            @Override
            public void onClick(final View v) {
            int days = Integer.parseInt(daysRenting.getText().toString());
            if (days > 30) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
                builder.setTitle("Contact Customer Support")
                        .setMessage("To rent our vehicles for more than 30 days, please contact a customer representative at: \n\n512-777-222")
                        .setPositiveButton("Call Now", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Uri number = Uri.parse("tel:5127772222");
                                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                                startActivity(callIntent);
                            }
                        })
                        .setNegativeButton("Back", null)
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();

            } else if (days > 0){
                double total = days * dailyRate;
                Toast.makeText(DetailActivity.this, "Your total rental cost is " + dollar.format(total) + ".", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(DetailActivity.this, "Please enter a positive integer.", Toast.LENGTH_LONG).show();
            }

            }
        });

        updateRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetailActivity.this, UpdateActivity.class));
            }
        });

    }
}
