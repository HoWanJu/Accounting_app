package com.example.accounting_app;

import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    private EditText accountEdit;
    private EditText passwordEdit;
    private EditText passwordEditEnsure;
    Button signupBtn;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();
        accountEdit = findViewById(R.id.editText_account);
        passwordEdit = findViewById(R.id.editText_password);
        passwordEditEnsure = findViewById(R.id.editText_ensure_password);

        signupBtn = findViewById(R.id.button_signUp);
        loginBtn = findViewById(R.id.button_login_again);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account = accountEdit.getText().toString();
                String password = passwordEdit.getText().toString();
                String password_ensure = passwordEditEnsure.getText().toString();

                if(TextUtils.isEmpty(account)){
                    Toast.makeText(SignupActivity.this, R.string.account_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(SignupActivity.this, R.string.password_empty, Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(password_ensure)){
                    Toast.makeText(SignupActivity.this, R.string.password_ensure, Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(account, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent();
                            intent.setClass(SignupActivity.this, ChooseRole.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(SignupActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
