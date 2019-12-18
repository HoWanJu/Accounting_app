package com.example.accounting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Bundle;

import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class History extends AppCompatActivity {
    String incomeStr;
    String expensesStr;
    TextView incomeTv;
    TextView expensesTv;
    TextView categoryTv;


    DatePicker datePicker;
    TextView dateTv;

    //被選擇的日期
    int year;
    int month;
    int date;


    Button okBtn;
    Button cancelBtn;

    ImageButton backBTN;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        goBack();
        mContext = History.this;

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
        for(int i = 0;i<30;i++) {

            //抓當日紀錄
            //定義要加在哪一個layout
            final LayoutInflater inflater = LayoutInflater.from(this);
            LinearLayout tobe_add_LT = view.findViewById(R.id.records_layout);
            //定義要加進去的layout
            LinearLayout add_LT = inflater.inflate(R.layout.day_record_detail, null).findViewById(R.id.new_day_record_LT);
            //定義文字
            TextView newCategory;
            TextView newPrice;
            TextView newNote;
            newCategory = add_LT.findViewById(R.id.category_text);
            newPrice = add_LT.findViewById(R.id.price_text);
            newNote = add_LT.findViewById(R.id.note_text);

            newCategory.setText("早餐");
            newPrice.setText("50");
            newNote.setText("檸檬紅茶+雞排+雞塊");
            int incomIS_0_payIS_1 = 0;
            if (incomIS_0_payIS_1 == 1){
                newCategory.setTextColor(Color.parseColor("#3F51B5"));
            }


            //把layout加進
            tobe_add_LT.addView(add_LT);
        }

        //設定總收入&支出
        int dayIncome = 110;
        int dayExpenses = 20;
        incomeTv = view.findViewById(R.id.inMoney);
        expensesTv = view.findViewById(R.id.exMoney);
        incomeStr = String.valueOf(dayIncome) +"元";
        expensesStr = String.valueOf(dayExpenses) + "元";
        incomeTv.setText(incomeStr);
        expensesTv.setText(expensesStr);

        //設定日期
        dateTv = view.findViewById(R.id.date);
        dateTv.setText(year + "年" + month + "月" + date + "日" );


        //視窗寬高
        final PopupWindow popupWindow = new PopupWindow(view,ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
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
        //設定取消按鈕
        cancelBtn = view.findViewById(R.id.cancel);
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
                History.this.finish();
            }
        });
    }






}