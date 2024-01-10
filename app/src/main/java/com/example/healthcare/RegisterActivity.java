package com.example.healthcare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText edUsername, edPassword, edEmail, edConfirm;
    Button registerBtn;
    TextView backBtnTxt;

    public static boolean IsValid(String passwordhere) {
        int f1 = 0, f2 = 0, f3 = 0;
        if (passwordhere.length() < 8) {
            return false;
        } else {
            for (int p = 0; p < passwordhere.length(); p++) {
                if (Character.isLetter(passwordhere.charAt(p))) {
                    f1 = 1;
                }
            }
            for (int r = 0; r < passwordhere.length(); r++) {
                if (Character.isDigit(passwordhere.charAt(r))) {
                    f2 = 1;
                }
            }
            for (int s = 0; s < passwordhere.length(); s++) {
                char c = passwordhere.charAt(s);
                if (c >= 33 && c <= 46) {
                    f3 = 1;
                }
            }
            if (f1 == 1 && f3 == 1)
                return true;
            return false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edUsername = findViewById(R.id.fullnameTxtBYO);
        edPassword = findViewById(R.id.AddressTxtBYO);
        edEmail = findViewById(R.id.emailTxt);
        edConfirm = findViewById(R.id.contactnoTxtBYO);
        registerBtn = findViewById(R.id.bookBtn);
        backBtnTxt = findViewById(R.id.backBtnTxt);

        backBtnTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edUsername.getText().toString();
                String email = edEmail.getText().toString();
                String password = edPassword.getText().toString();
                String confirm = edConfirm.getText().toString();

                if (username.length() == 0 || email.length() == 0 || password.length() == 0 || confirm.length() == 0) {
                    Toast.makeText(getApplicationContext(), "Please fill All details", Toast.LENGTH_SHORT).show();
                } else {

                    if (password.equals(confirm)) {


                        Database db = new Database(getApplicationContext());

                        boolean userAlreadyExist = db.checkUsername(username);

                        if (userAlreadyExist) {
                            Toast.makeText(getApplicationContext(), "Username is already taken. Try different one !", Toast.LENGTH_SHORT).show();
                        } else {
                            boolean isRegisterSuccess = db.register(username, password, email);

                            if (isRegisterSuccess) {
                                Toast.makeText(getApplicationContext(), "Registered successful!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                            } else {
                                Toast.makeText(getApplicationContext(), "Registered Unsuccessful!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Password and Confirm password didn't match", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });
    }
}