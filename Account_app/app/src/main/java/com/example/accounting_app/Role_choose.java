package com.example.accounting_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.ImageButton;

public class Role_choose extends AppCompatActivity {
    ImageButton imBtn1;
    ImageButton imBtn2;
    ImageButton imBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role_choose);
        setMainControl();
    }
    private void setMainControl(){
        imBtn1 = (ImageButton)findViewById(R.id.momBtn);
        imBtn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                show(1);
            }
        });

        imBtn2 = (ImageButton)findViewById(R.id.dadBtn);
        imBtn2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                show(2);
            }
        });

        imBtn3 = (ImageButton)findViewById(R.id.expertBtn);
        imBtn3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                show(3);
            }
        });
    }

    private void show(int i) {
        switch (i) {
            case 1:
                alertdialog("媽媽");
                break;

            case 2:
                alertdialog("爸爸");
                break;

            case 3:
                alertdialog("理財專家");

            default:
                break;
        }
    }
    private void alertdialog(String str){
        new AlertDialog.Builder(Role_choose.this)
                .setTitle("確定要選擇"+ str +"嗎")
                .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Role_choose.this, Main2Activity.class);
                            startActivity(intent);
                    }
                }).setNegativeButton("取消", null).create().show();
    }
}