package com.example.accounting_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Budget extends AppCompatActivity {
    ImageButton backBTN;
    Button confirmBTN;
    EditText inputEdT;
    String confirmStr;

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
//                Intent intent = new Intent(Budget.this, Function.class);
//                startActivity(intent);
            }
        });
    }
    //確認
    private void confirm(){
        confirmBTN = (Button)findViewById(R.id.confirmBtn);
        confirmBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEdT = findViewById(R.id.inputET);
                if("".equals(inputEdT.getText().toString().trim())){
                    new AlertDialog.Builder(Budget.this)
                            .setTitle("請輸入金額")
                            .setPositiveButton("確定", null).create().show();
                }
                else{
                    budgetAlertDialog();
                }
            }
        });
    }

    private void budgetAlertDialog(){
        new AlertDialog.Builder(Budget.this)
                .setTitle("確定要輸入預算嗎")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        confirmStr = "已輸入金額"+inputEdT.getText().toString()+"元";
                        Toast.makeText(Budget.this,confirmStr, Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Budget.this, Function.class);
                        startActivity(intent);
                    }
                }).setNegativeButton("取消", null).create().show();
    }

}
