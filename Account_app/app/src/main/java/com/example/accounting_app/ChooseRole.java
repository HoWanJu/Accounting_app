package com.example.accounting_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


import com.example.accounting_app.chatting_page.Voice_Assistant;

import java.util.ArrayList;
import java.util.List;


public class ChooseRole extends AppCompatActivity {
    private ViewPager viewPager;//頁面内容
    private TextView textView1,textView2,textView3;//頁面標題
    private List<View> views;// 頁面列表
    private int currIndex = 0;// 當頁編號
    private View view1,view2,view3;//各個頁面
    private Button comfirmBtn; //確認鈕
    private String str;//alertdialog字串

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_role);
        InitTextView();
        InitViewPager();

    }

    //初始化頁面
    private void InitViewPager() {
        viewPager=(ViewPager) findViewById(R.id.vPager);
        views=new ArrayList<View>();
        LayoutInflater inflater=getLayoutInflater();
        view1=inflater.inflate(R.layout.mom_layout, null);
        view2=inflater.inflate(R.layout.dad_layout, null);
        view3=inflater.inflate(R.layout.expert_layout, null);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }


    //初始化標頭
    private void InitTextView() {
        textView1 = (TextView) findViewById(R.id.page_mom);
        textView2 = (TextView) findViewById(R.id.page_dad);
        textView3 = (TextView) findViewById(R.id.page_expert);
        textView1.setBackgroundResource(R.drawable.role_seleted);
        textView1.setOnClickListener(new MyOnClickListener(0));
        textView2.setOnClickListener(new MyOnClickListener(1));
        textView3.setOnClickListener(new MyOnClickListener(2));
    }



    //標頭點選
    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            textView1.setBackgroundResource(R.drawable.role_unseleted);
            textView2.setBackgroundResource(R.drawable.role_unseleted);
            textView3.setBackgroundResource(R.drawable.role_unseleted);
            viewPager.setCurrentItem(index);
            changecolor(index);
        }
    }
        //滑動頁面
        public class MyViewPagerAdapter extends PagerAdapter{
            private List<View> mListViews;

            public MyViewPagerAdapter(List<View> mListViews) {
                this.mListViews = mListViews;
            }
            //將原本的頁面移除
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) 	{
                container.removeView(mListViews.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                container.addView(mListViews.get(position), 0);
                return mListViews.get(position);
            }

            @Override
            public int getCount() {
                return  mListViews.size();
            }

            @Override
            public boolean isViewFromObject(View arg0, Object arg1) {
                return arg0==arg1;
            }
        }
        public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

            public void onPageScrollStateChanged(int arg0) {
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageSelected(int arg0) {
                currIndex = arg0;
                switch (currIndex){
                    case 0:
                        textView1.setBackgroundResource(R.drawable.role_seleted);
                        textView2.setBackgroundResource(R.drawable.role_unseleted);
                        textView3.setBackgroundResource(R.drawable.role_unseleted);
                        str = "媽媽";
                        break;
                    case 1:
                        textView2.setBackgroundResource(R.drawable.role_seleted);
                        textView1.setBackgroundResource(R.drawable.role_unseleted);
                        textView3.setBackgroundResource(R.drawable.role_unseleted);
                        str = "爸爸";
                        break;
                    case 2:
                        textView3.setBackgroundResource(R.drawable.role_seleted);
                        textView2.setBackgroundResource(R.drawable.role_unseleted);
                        textView1.setBackgroundResource(R.drawable.role_unseleted);
                        str = "理財專家";
                        break;
                }
                comfirmBtn = findViewById(R.id.confirm);
                comfirmBtn.setOnClickListener(new View.OnClickListener(){
                    public void onClick(View v){
                        alertdialog(str);
                    }
                });
            }
            private void alertdialog(String str){
                new AlertDialog.Builder(ChooseRole.this)
                        .setTitle("確定要選擇"+ str +"嗎？")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(ChooseRole.this, Voice_Assistant.class);
                                startActivity(intent);
                            }
                        }).setNegativeButton("取消", null).create().show();
            }

        }
    //變換標頭顏色
    public void changecolor(int page){
        currIndex = page;
        switch (currIndex){
            case 0:
                textView1.setBackgroundResource(R.drawable.role_seleted);
                textView2.setBackgroundResource(R.drawable.role_unseleted);
                textView3.setBackgroundResource(R.drawable.role_unseleted);
                break;
            case 1:
                textView2.setBackgroundResource(R.drawable.role_seleted);
                textView1.setBackgroundResource(R.drawable.role_unseleted);
                textView3.setBackgroundResource(R.drawable.role_unseleted);
                break;
            case 2:
                textView3.setBackgroundResource(R.drawable.role_seleted);
                textView2.setBackgroundResource(R.drawable.role_unseleted);
                textView1.setBackgroundResource(R.drawable.role_unseleted);
                break;
        }
    }
}
