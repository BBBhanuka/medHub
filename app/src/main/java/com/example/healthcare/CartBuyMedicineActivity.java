package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CartBuyMedicineActivity extends AppCompatActivity {
    HashMap<String, String> item;
    ArrayList list;
    SimpleAdapter sa;
    TextView tvTotal;
    ListView lst;

    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, btnCheckout, btnBack, btnClear;
    private String[][] packages = {};

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_buy_medicine);

        dateButton = findViewById(R.id.buttonBMCartDate);
        btnCheckout = findViewById(R.id.buttonMOCartCheckout);
        btnBack = findViewById(R.id.buttonMODBack);
        btnClear = findViewById(R.id.buttonClearCart);
        tvTotal = findViewById(R.id.textViewMOCartTotalCost);
        lst = findViewById(R.id.listViewMO);


        Database db = new Database(getApplicationContext());


        Cursor results = db.getCurrentCart();

        list = new ArrayList();
        double finalTotal = 0;


        while (results.moveToNext()) {
            String medName = results.getString(1);
            int medQuantity = Integer.parseInt(results.getString(2));
            double medPrice = Double.parseDouble(results.getString(3));
            double totalCost = medQuantity * medPrice;

            item = new HashMap<String, String>();
            item.put("medName", medName);
            item.put("medQTY", medQuantity + " nos");
            item.put("medPrice", medPrice + "/=");
            item.put("other1", "");
            item.put("totalCost", totalCost + "/=");
            list.add(item);

            finalTotal += totalCost;

        }


        sa = new SimpleAdapter(this, list,
                R.layout.multi_lines,
                new String[]{"medName", "medQTY", "medPrice", "other1", "totalCost"},
                new int[]{R.id.line_a, R.id.line_b, R.id.line_c, R.id.line_d, R.id.line_e});
        lst.setAdapter(sa);

        tvTotal.setText("Total Amount : " + finalTotal);


        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int recordCount = db.getItemCountCart();

                if(recordCount > 0) {

                    androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(CartBuyMedicineActivity.this);

                    // Set the message show for the Alert time
                    builder.setMessage(recordCount + " Item/s in Cart.\nAre you sure to clear shopping Cart? ");

                    // Set Alert Title
                    builder.setTitle("Warning !");

                    // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
                    builder.setCancelable(false);

                    // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
                    builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {

                        // When the user click yes button then table will clear

                        boolean isCleared = db.clearTempDB();

                        if (isCleared) {
                            Toast.makeText(getApplicationContext(), recordCount + " number of records cleared !", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Error in Operation", Toast.LENGTH_SHORT).show();
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
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
              //  startActivity(new Intent(CartBuyMedicineActivity.this, BuyMedicineActivity.class));
            }
        });

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(CartBuyMedicineActivity.this, BuyMedicineBookActivity.class);
                it.putExtra("price", tvTotal.getText());
                it.putExtra("date", dateButton.getText());
                startActivity(it);
            }
        });

        //datepicker
        initDatePicker();
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Get the data associated with the touched item
                Map<String, Object> itemData = (Map<String, Object>) parent.getItemAtPosition(position);

                // Get the "medName" value
                String medName = (String) itemData.get("medName");

                Cursor data = db.getDataforUpdate(medName);

                String medQuantity = null;
                String medPrice = null;


                if (data.moveToFirst()) {

                    if (medName.equals(data.getString(0))) {
                        medQuantity = data.getString(1);
                        medPrice = data.getString(2);

                    }

                }

                finish();
                Intent it = new Intent(CartBuyMedicineActivity.this, UpdateMedCart.class);
                it.putExtra("medName", medName);
                it.putExtra("medQty", medQuantity);
                it.putExtra("medPrice", medPrice);
                startActivity(it);

            }
        });

    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                i1 = i1 + 1;
                dateButton.setText(i2 + "/" + i1 + "/" + i);
            }
        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        datePickerDialog.getDatePicker().setMinDate(cal.getTimeInMillis() + 86400000);
    }


}