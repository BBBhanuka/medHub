package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UpdateMedCart extends AppCompatActivity {

    TextView txtMedName, txtMedPrice, txtMedQuantity;
    Button btnUpdate, btnBack, btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_med_cart);

        txtMedName = findViewById(R.id.TextMedicineName);
        txtMedPrice = findViewById(R.id.textViewMedPrice);
        txtMedQuantity = findViewById(R.id.textViewMedCurQty);


        btnUpdate = findViewById(R.id.buttonUpdateQTY);
        btnBack = findViewById(R.id.buttonUpdateBack);
        btnDelete = findViewById(R.id.buttonUpdateDelete);


        Intent intent = getIntent();
        String doubleMedPrice = intent.getStringExtra("medPrice");
        String strMedName = intent.getStringExtra("medName");
        String intMedQTY = intent.getStringExtra("medQty");

        txtMedName.setText(strMedName);
        txtMedPrice.setText(doubleMedPrice);
        txtMedQuantity.setText(intMedQTY);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UpdateMedCart.this,CartBuyMedicineActivity.class));
            }
        });


    }
}