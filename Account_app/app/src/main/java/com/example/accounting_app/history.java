package com.example.accounting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Bundle;

import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.TextView;

public class history extends AppCompatActivity {
    String incomeStr;
    String expensesStr;
    TextView incomeTv;
    TextView expensesTv;
    int dayIncome = 0;
    int dayExpenses = 0;

    DatePicker datePicker;
    TextView dateTv;
    int year;
    int month;
    int date;


    Button okBtn;
    ImageButton cancelBtn;

    ImageButton backBTN;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        goBack();
        mContext = history.this;

        datePicker = findViewById(R.id.datepicker);

        okBtn = (Button) findViewById(R.id.OK);
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                year = datePicker.getYear();
                month = datePicker.getMonth()+1;
                date = datePicker.getDayOfMonth();

                initPopupWindow(v);
            }
        });
    }


    private void initPopupWindow(View v){
        View view = LayoutInflater.from(mContext).inflate(R.layout.day_record,null,false);
        cancelBtn = view.findViewById(R.id.cancel);
        incomeTv = view.findViewById(R.id.inMoney);
        expensesTv = view.findViewById(R.id.exMoney);
        incomeStr = String.valueOf(dayIncome) +"元";
        expensesStr = String.valueOf(dayExpenses) + "元";
        incomeTv.setText(incomeStr);
        expensesTv.setText(expensesStr);

        dateTv = view.findViewById(R.id.date);
        dateTv.setText(year + "年" + month + "月" + date + "日" );
        //視窗寬高
        final PopupWindow popupWindow = new PopupWindow(view,1000,ViewGroup.LayoutParams.WRAP_CONTENT,true);
       //點其他地方popwindow會消失
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        //設置背景
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        //POP視窗位置
        popupWindow.showAtLocation(okBtn, Gravity.CENTER,0,0);
       //視窗中按鈕事件
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }


    //回到上一頁
    private void goBack() {
        backBTN = (ImageButton) findViewById(R.id.backBtn);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            //Main2Activity is the page to test.
            public void onClick(View v) {
                Intent intent = new Intent(history.this, function.class);
                startActivity(intent);
            }
        });
    }


}
