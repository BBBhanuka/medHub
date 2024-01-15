package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BuyMedicineBookActivity extends AppCompatActivity {
    EditText edName, edContact, edAddress;
    Button btnBooking, btnCurLocation;

    TextView locLable;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_book);

        edName = findViewById(R.id.editTextFullName);
        edAddress = findViewById(R.id.editTextAddress);
        edContact = findViewById(R.id.editTextContact);
        btnBooking = findViewById(R.id.buttonBMBBooking);
        locLable = findViewById(R.id.textViewLocationLable);
        btnCurLocation = findViewById(R.id.btnCurrentLocation);


        if (AppGlobal.userLatitude == 0 && AppGlobal.userLongitude == 0) {
            locLable.setText("Location is not set");
        } else {
            locLable.setText("Location is set");

        }

        edName.setText(AppGlobal.fullName);
        edContact.setText(AppGlobal.contactNumber);


        btnCurLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (!edAddress.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Address must be empty to select current location", Toast.LENGTH_SHORT).show();
                } else {
                    if (!edName.getText().toString().isEmpty() && !edContact.getText().toString().isEmpty()) {
                        AppGlobal.fullName = edName.getText().toString();
                        AppGlobal.contactNumber = edContact.getText().toString();
                    }

                    finish();
                    startActivity(new Intent(BuyMedicineBookActivity.this, MapsActivity.class));
                }
            }
        });

        btnBooking.setOnClickListener(new View.OnClickListener() {
            public Boolean getText() {
                Boolean text = null;
                return text;
            }

            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                String username = sharedPreferences.getString("username", "").toString();

//                Database db = new Database(getApplicationContext());
//                db.addOrder(username,edname,getText().toString(),edcontact.getText().toString(),Integer.parseInt(edpincode.getText().toString()),date.toString(), "",Float.parseFloat(price[1].toString()),"medicine");
//                db.removeCart(username,"medicine");
//                Toast.makeText(getApplicationContext(),"Your booking is done successfully",Toast.LENGTH_LONG).show();
                startActivity(new Intent(BuyMedicineBookActivity.this, HomeActivity.class));
            }

        });
    }
}