package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BuyMedicineBookActivity extends AppCompatActivity {
    EditText edName, edContact, edAddress;
    Button btnBooking, btnCurLocation, btnClearLocation;

    TextView locLable, lblMedCount, lblTotalPrice;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_book);

        Database db = new Database(getApplicationContext());

        edName = findViewById(R.id.editTextFullName);
        edAddress = findViewById(R.id.editTextAddress);
        edContact = findViewById(R.id.editTextContact);
        btnBooking = findViewById(R.id.buttonBMBBooking);
        locLable = findViewById(R.id.textViewLocationLable);
        btnCurLocation = findViewById(R.id.btnCurrentLocation);
        btnClearLocation = findViewById(R.id.btnClearLocation);
        lblMedCount = findViewById(R.id.textViewMedCount);
        lblTotalPrice = findViewById(R.id.textViewTotalAmount);


        int itemCount = db.getItemCountCart();

        lblMedCount.setText("Medicine Count : " + itemCount);
        lblTotalPrice.setText("Total Amount : " + AppGlobal.totalCartAmount);



        if (AppGlobal.userLatitude == 0 && AppGlobal.userLongitude == 0) {
            locLable.setText("Location is not set");
            btnClearLocation.setEnabled(false);

        } else {
            locLable.setText("Location is set");
            btnClearLocation.setEnabled(true);
        }

        edName.setText(AppGlobal.fullName);
        edContact.setText(AppGlobal.contactNumber);


        locLable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AppGlobal.userLatitude != 0 && AppGlobal.userLongitude != 0) {
                    Toast.makeText(getApplicationContext(), "User Latitude : " + AppGlobal.userLatitude +"\nUser Longitude : " + AppGlobal.userLongitude, Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(getApplicationContext(), "Location is not set", Toast.LENGTH_SHORT).show();
                }
            }
        });


        edAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if(edAddress.getText().toString().trim().isEmpty()){
                    btnCurLocation.setEnabled(true);
                }
                else {
                    btnCurLocation.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        btnCurLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (!edName.getText().toString().isEmpty() && !edContact.getText().toString().isEmpty()) {
                        AppGlobal.fullName = edName.getText().toString();
                        AppGlobal.contactNumber = edContact.getText().toString();
                    }

                    finish();
                    startActivity(new Intent(BuyMedicineBookActivity.this, MapsActivity.class));
                }

        });

        btnClearLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locLable.setText("Location is not set");
                AppGlobal.userLongitude = 0;
                AppGlobal.userLatitude = 0;
                btnClearLocation.setEnabled(false);
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