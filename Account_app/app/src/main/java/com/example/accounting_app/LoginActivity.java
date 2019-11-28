package com.example.accounting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.cloud.Role;

public class LoginActivity extends AppCompatActivity {

    EditText account;
    EditText password;
    Button loginBtn;
    TextView signup;
    TextView forgetpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setContentView(R.layout.activity_login);
        account = findViewById(R.id.editText_account);
        password = findViewById(R.id.editText_password);
        loginBtn = findViewById(R.id.button_login);
        signup = findViewById(R.id.textView_signUp);
        forgetpassword = findViewById(R.id.textView_forgetPas);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String accountStr = account.getText().toString();
                String passwordStr = password.getText().toString();

                if(accountStr.equals("")){
                    Toast.makeText(LoginActivity.this,"請輸入帳號",Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent();
                    intent.setClass(LoginActivity.this, Role_choose.class);
                    startActivity(intent);
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, ForgetPassword.class);
                startActivity(intent);
            }
        });

    }
}
