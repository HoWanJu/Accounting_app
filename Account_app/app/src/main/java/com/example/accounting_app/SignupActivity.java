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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity {
    public FirebaseAuth mAuth;
    private String uid;
    private FirebaseUser user;
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
                            user = FirebaseAuth.getInstance().getCurrentUser();
                            uid = user.getUid();
                            //wirte to database
                            //連接資料庫
                            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                            // user setting
                            DatabaseReference myRef_user = firebaseDatabase.getReference("user_profile");
                            User user = new User("媽媽");     // 預設角色為媽媽
                            myRef_user.child(uid).setValue(user);


                            // accounting_record setting
                            DatabaseReference myRef_accounting = firebaseDatabase.getReference("accounting_record");
                            accounting_month accounting_month = new accounting_month(0, 0, 0);
                            accounting_day accounting_day = new accounting_day(0, 0);

                            for(Integer month=1; month<=3; month++) {
                                if(month == 1) {   // 11月
                                    myRef_accounting.child(uid).child("2019").child("11").setValue(accounting_month);
                                    for(Integer day=1; day<=30; day++) {
                                        String sDay = Integer.toString(day);
                                        myRef_accounting.child(uid).child("2019").child("11").child(sDay).setValue(accounting_day);
                                    }
                                }
                                if(month == 2) {   // 12月
                                    myRef_accounting.child(uid).child("2019").child("12").setValue(accounting_month);
                                    for(Integer day=1; day<=31; day++) {
                                        String sDay = Integer.toString(day);
                                        myRef_accounting.child(uid).child("2019").child("12").child(sDay).setValue(accounting_day);
                                    }
                                }
                                if(month == 3) {   // 1月
                                    myRef_accounting.child(uid).child("2020").child("1").setValue(accounting_month);
                                    for(Integer day=1; day<=30; day++) {
                                        String sDay = Integer.toString(day);
                                        myRef_accounting.child(uid).child("2020").child("1").child(sDay).setValue(accounting_day);
                                    }
                                }
                            }


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
