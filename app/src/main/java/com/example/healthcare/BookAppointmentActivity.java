package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class BookAppointmentActivity extends AppCompatActivity {

    EditText fullnameTxt, addressTxt, contactNoTxt, reposrtsTxt;
    private DatePickerDialog datePickerDialog;
    private TimePickerDialog timePickerDialog;
    private Button dateButton, timeButton, btnBook, btnBack;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_appointment);

        fullnameTxt = findViewById(R.id.fullnameTxt);
        addressTxt = findViewById(R.id.addressTxt);
        contactNoTxt = findViewById(R.id.contactNoTxt);
        reposrtsTxt = findViewById(R.id.reportsTxt);

        dateButton = findViewById(R.id.dateSelectBtn);
        timeButton = findViewById(R.id.timeSelectBtn);
        btnBook = findViewById(R.id.buttonBMBBooking);
        btnBack = findViewById(R.id.buttonAppBack);

        //datepicker
        initDatePicker();

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        //timepicker
        initTimePicker();

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog.show();
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(BookAppointmentActivity.this,HomeActivity.class));
                finish();
            }
        });

        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Database db = new Database(getApplicationContext());

                String strFullName = fullnameTxt.getText().toString();
                String strAddress = addressTxt.getText().toString();
                String strContactNo = contactNoTxt.getText().toString();
                String strReportText = reposrtsTxt.getText().toString();
                String strDate = dateButton.getText().toString();
                String strTime = timeButton.getText().toString();

                if (strFullName.trim().isEmpty() || strAddress.trim().isEmpty() || strContactNo.trim().isEmpty() || strReportText.trim().isEmpty() || strTime.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "All inputs are required !", Toast.LENGTH_SHORT).show();

                } else {
                    db.bookAppointment(strFullName, strAddress, strContactNo, strReportText, strDate, strTime);
                    Toast.makeText(getApplicationContext(), "Your appointment is done successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    //startActivity(new Intent(BookAppointmentActivity.this, HomeActivity.class));
                }


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

    private void initTimePicker() {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                i1 = i1 + 1;
                timeButton.setText(i + ":" + i1);
            }
        };
        Calendar cal = Calendar.getInstance();
        int hrs = cal.get(Calendar.HOUR);
        int mins = cal.get(Calendar.MINUTE);

        int style = AlertDialog.THEME_HOLO_DARK;
        timePickerDialog = new TimePickerDialog(this, style, timeSetListener, hrs, mins, true);
    }
}
