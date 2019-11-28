package com.example.accounting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class budget extends AppCompatActivity {
    ImageButton backBTN;
    Button confirmBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budget);
        goBack();
        confirm();
    }
    //回到上一頁
    private void goBack(){
        backBTN = (ImageButton)findViewById(R.id.backBtn);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            //Main2Activity is the page to test.
            public void onClick(View v) {
                Intent intent = new Intent(budget.this, Main2Activity.class);
                startActivity(intent);
            }
        });
    }
    //確認
    private void confirm(){
        confirmBTN = (Button)findViewById(R.id.confirmBtn);
        confirmBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(budget.this, Main2Activity.class);
                startActivity(intent);
            }
        });
    }
}
