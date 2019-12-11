package com.example.accounting_app;

import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Category extends AppCompatActivity{
    ScrollView payView1;
    ScrollView payView2;
    ScrollView incomeView1;
    ScrollView incomeView2;
    Button showPay;
    Button showIncome;
    Button add;

    EditText inputCategory;
    String inputName;
    String str;
    ImageView backBTN;

    //判斷在哪一頁
    int payORimcome = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category);
        goBack();
        changeView();
        add_category();
    }
    //回到上一頁
    private void goBack() {
        backBTN = findViewById(R.id.pre_page);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            //Main2Activity is the page to test.
            public void onClick(View v) {
                Category.this.finish();
            }
        });
    }

    public void changeView(){
        payView1 = findViewById(R.id.payScrollView1);
        payView1.setVisibility(View.VISIBLE);
        payView2 = findViewById(R.id.payScrollView2);
        payView2.setVisibility(View.VISIBLE);


        incomeView1 = findViewById(R.id.incomeScrollView1);
        incomeView1.setVisibility(View.INVISIBLE);
        incomeView2 = findViewById(R.id.incomeScrollView2);
        incomeView2.setVisibility(View.INVISIBLE);

        showPay = findViewById(R.id.pay_btn);
        showIncome = findViewById(R.id.income_btn);

        showPay.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                payView1.setVisibility(View.VISIBLE);
                payView2.setVisibility(View.VISIBLE);
                incomeView1.setVisibility(View.INVISIBLE);
                incomeView2.setVisibility(View.INVISIBLE);
                payORimcome = 0;
            }
        });

        showIncome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                incomeView1.setVisibility(View.VISIBLE);
                incomeView2.setVisibility(View.VISIBLE);
                payView1.setVisibility(View.INVISIBLE);
                payView2.setVisibility(View.INVISIBLE);
                payORimcome = 1;
            }
        });
    }
    //新增類別名稱
    public void add_category(){
        add = findViewById(R.id.add_btn);
        add.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                final EditText input = new EditText(Category.this);
                input.setHint("名稱");
                AlertDialog.Builder builder = new AlertDialog.Builder(Category.this);
                builder.setTitle("請輸入要新增的類別");
                LayoutInflater inflater = Category.this.getLayoutInflater();
                //設定dialog的內容是input
                builder.setView(input);
                builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        inputName = input.getText().toString();
                        if("".equals(inputName)){
                            Toast.makeText(Category.this,"請輸入名稱", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            str = "已將"+inputName+"新增至類別";
                            Toast.makeText(Category.this,str, Toast.LENGTH_SHORT).show();
                            editCategory();
                        }
                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }


    //將新增的類別加至頁面中
    public void editCategory(){
        final LayoutInflater inflater = LayoutInflater.from(this);
        final LinearLayout tobe_add_LT = findViewById(R.id.addPay);
        final LinearLayout tobe_add_LT1 = findViewById(R.id.addIncome);
        //獲取新添加的layout檔案及id
        RelativeLayout add_LT = (RelativeLayout) inflater.inflate(
                R.layout.new_category_layout, null).findViewById(R.id.new_category_layout);
        //設定類別名稱
        TextView newName;
        newName = add_LT.findViewById(R.id.name);
        newName.setText(inputName);
        // 將新添加的layout加入要加的layout中
        if(payORimcome == 0){
            tobe_add_LT.addView(add_LT);
        }
        else if(payORimcome == 1){
            tobe_add_LT1.addView(add_LT);
        }
        //刪除類別
        add_LT.findViewById(R.id.trash_new).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(payORimcome == 0){
                    tobe_add_LT.removeView(add_LT);
                }
                else if(payORimcome == 1){
                    tobe_add_LT1.removeView(add_LT);
                }
                Toast.makeText(Category.this,"已刪除", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
