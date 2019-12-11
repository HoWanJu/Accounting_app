package com.example.accounting_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class Report extends AppCompatActivity {

    ImageButton backBTN;
    TextView title;
    TextView source;
    TextView author;
    TextView Content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        title = findViewById(R.id.title);
        source = findViewById(R.id.source);
        author = findViewById(R.id.author);
        Content = findViewById(R.id.content);
        goBack();
    }

    //回到上一頁
    private void goBack() {
        backBTN = (ImageButton) findViewById(R.id.backBtn);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            //Main2Activity is the page to test.
            public void onClick(View v) {
                Intent intent = new Intent(Report.this, Function.class);
                startActivity(intent);
            }
        });
    }
}
