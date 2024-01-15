package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.app.AlertDialog;


import java.util.concurrent.ExecutionException;

public class UpdateMedCart extends AppCompatActivity {

    TextView txtMedName, txtMedPrice, txtMedQuantity;
    Button btnUpdate, btnBack, btnDelete;

    EditText txtNewMedQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_med_cart);

        Database db = new Database(getApplicationContext());

        txtMedName = findViewById(R.id.TextMedicineName);
        txtMedPrice = findViewById(R.id.textViewMedPrice);
        txtMedQuantity = findViewById(R.id.textViewMedCurQty);

        txtNewMedQty = findViewById(R.id.editTextQuantity);


        btnUpdate = findViewById(R.id.buttonUpdateQTY);
        btnBack = findViewById(R.id.buttonUpdateBack);
        btnDelete = findViewById(R.id.buttonUpdateDelete);


        Intent intent = getIntent();
        String doubleMedPrice = intent.getStringExtra("medPrice");
        String strMedName = intent.getStringExtra("medName");
        String intMedQTY = intent.getStringExtra("medQty");

        txtMedName.setText(strMedName);
        txtMedPrice.setText("Price : " + doubleMedPrice);
        txtMedQuantity.setText("Current QTY : " + intMedQTY);

        txtNewMedQty.getText();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateMedCart.this, CartBuyMedicineActivity.class));
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(UpdateMedCart.this);

                    // Set the message show for the Alert time
                    builder.setMessage("Medicine Name : " + strMedName + "\nAre you sure to remove above medicine from your cart?  ");

                    // Set Alert Title
                    builder.setTitle("Warning !");

                    // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                    builder.setCancelable(false);

                    // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                    builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {

                        // When the user click yes button then table will clear

                        try {
                            boolean isDeleted = db.deleteMedicine(strMedName);

                            if (isDeleted) {
                                Toast.makeText(getApplicationContext(), "Medicine Deleted Successfully", Toast.LENGTH_SHORT).show();

                                int recordCount = db.getItemCountCart();
                                finish();

                                if (recordCount == 0) {
                                    Toast.makeText(getApplicationContext(), "Shopping cart is empty.", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(UpdateMedCart.this, BuyMedicineActivity.class));
                                } else {
                                    startActivity(new Intent(UpdateMedCart.this, CartBuyMedicineActivity.class));
                                }

                            } else {
                                Toast.makeText(getApplicationContext(), "Operation Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

                    // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
                    builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
                        // If user click no then dialog box is canceled.
                        dialog.cancel();
                    });

                    // Create the Alert dialog
                    androidx.appcompat.app.AlertDialog alertDialog = builder.create();
                    // Show the Alert Dialog box
                    alertDialog.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    int newMedQTY = Integer.parseInt(txtNewMedQty.getText().toString());

                    if (newMedQTY <= 0) {
                        Toast.makeText(getApplicationContext(), "Quantity must greater than 0", Toast.LENGTH_SHORT).show();
                    } else {
                        boolean isUpdated = db.updateAddedQTYIndex(strMedName, newMedQTY);

                        if (isUpdated) {

                            Toast.makeText(getApplicationContext(), "Quantity Update Success", Toast.LENGTH_SHORT).show();
                            finish();
                            startActivity(new Intent(UpdateMedCart.this, CartBuyMedicineActivity.class));

                        } else {
                            Toast.makeText(getApplicationContext(), "Operation Unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}