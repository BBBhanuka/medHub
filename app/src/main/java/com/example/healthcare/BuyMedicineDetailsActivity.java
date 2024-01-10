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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class BuyMedicineDetailsActivity extends AppCompatActivity {

    TextView tvPackageName,tvTotalCost, totalCost;
    TextView edDetails;

    EditText medQuantity;
    Button btnBack,btnAddToCart;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_medicine_details);

        tvPackageName = findViewById(R.id.textViewHADTitle);
        edDetails = findViewById(R.id.listViewHA);
        edDetails.setKeyListener(null);
        tvTotalCost = findViewById(R.id.textViewMedPrice);
        btnBack = findViewById(R.id.buttonHADBack);
        btnAddToCart = findViewById(R.id.buttonBMCartCheckout);
        totalCost = findViewById(R.id.textViewMedTotalCost);
        medQuantity = findViewById(R.id.editTextQuantity);

        Intent intent = getIntent();

        int intMedPrice = Integer.parseInt(intent.getStringExtra("medPrice"));



        try {

            if(medQuantity.getText().toString().isEmpty()) {
                totalCost.setText("Enter Quantity");
            }

        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }



        tvPackageName.setText(intent.getStringExtra("medName"));
        edDetails.setText(intent.getStringExtra("medDescription"));
        tvTotalCost.setText("Unit Price : " + intMedPrice + "/-");


        medQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                try {

                    if(medQuantity.getText().toString().isEmpty()) {
                        totalCost.setText("Enter Quantity");
                    }
                    else {
                        int intMedQuantity = Integer.parseInt(medQuantity.getText().toString());
                        totalCost.setText("Total Cost : " + intMedPrice * intMedQuantity);
                    }

                }
                catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences sharedPreferences = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
//                String username = sharedPreferences.getString("username", "").toString();
//                String product = tvPackageName.getText().toString();
//                float price = Float.parseFloat(intent.getStringExtra("text3").toString());

//                Database db = new Database(getApplicationContext());
//
//                if (db.checkCart(username, product) == 1) {
//                    Toast.makeText(getApplicationContext(), "Product Already Added", Toast.LENGTH_SHORT).show();
//                } else {
//                    db = new Database(getApplicationContext());
//                    db.addCart(username, product, price, "medicine");
//                    Toast.makeText(getApplicationContext(), "Record Inserted to Cart", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(BuyMedicineDetailsActivity.this, BuyMedicineActivity.class));
//                }
            }
        });
    }
}