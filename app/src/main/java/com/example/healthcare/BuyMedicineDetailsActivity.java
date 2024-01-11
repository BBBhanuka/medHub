package com.example.healthcare;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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

    TextView tvPackageName, tvTotalCost, totalCost;
    TextView edDetails;

    EditText medQuantity;
    Button btnBack, btnAddToCart;

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
        String strMedName = intent.getStringExtra("medName");

        try {

            if (medQuantity.getText().toString().isEmpty()) {
                totalCost.setText("Enter Quantity");
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        tvPackageName.setText(strMedName);
        edDetails.setText(intent.getStringExtra("medDescription"));
        tvTotalCost.setText("Unit Price : " + intMedPrice + "/-");

        medQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {


                try {

                    if (medQuantity.getText().toString().isEmpty()) {
                        totalCost.setText("Enter Quantity");
                    } else {
                        int intMedQuantity = Integer.parseInt(medQuantity.getText().toString());
                        totalCost.setText("Total Cost : " + intMedPrice * intMedQuantity);
                    }

                } catch (Exception e) {
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


                try {
                    int intMedQuantity = Integer.parseInt(medQuantity.getText().toString());
                    Database db = new Database(getApplicationContext());

                    boolean isAlreadyAdded = db.checkCartforItem(strMedName);

                    if (isAlreadyAdded) {

                        Cursor count = db.getAlreadyAddedCount(strMedName);
                        if (count.moveToFirst()) {

                            int quantity = count.getInt(count.getColumnIndex("QTY"));

                            AlertDialog.Builder builder = new AlertDialog.Builder(BuyMedicineDetailsActivity.this);

                            // Set the message show for the Alert time
                            builder.setMessage("Item already in cart.\nOld Quantity : " + quantity + "\nNew Quantity : " + intMedQuantity + "\nUpdate Quantity ?");

                            // Set Alert Title
                            builder.setTitle("Warning !");

                            // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                            builder.setCancelable(false);

                            // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                            builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {

                                // When the user click yes button then table will clear

                                boolean isUpdated = db.updateAddedQTY(strMedName, Integer.parseInt(medQuantity.getText().toString()));

                                if (isUpdated) {
                                    Toast.makeText(getApplicationContext(), "Quantity Update Success", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error in Update Quantity", Toast.LENGTH_SHORT).show();
                                }
                            });

                            // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                            builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                                // If user click no then dialog box is canceled.
                                dialog.cancel();
                            });

                            // Create the Alert dialog
                            AlertDialog alertDialog = builder.create();
                            // Show the Alert Dialog box
                            alertDialog.show();

                        }

                    } else {
                        boolean isAdded = db.addToCart(strMedName, Integer.parseInt(medQuantity.getText().toString()), Float.parseFloat(Integer.toString(intMedPrice)));

                        if (isAdded) {
                            Toast.makeText(getApplicationContext(), "Added Success", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error in Adding item", Toast.LENGTH_SHORT).show();
                        }
                    }


                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}