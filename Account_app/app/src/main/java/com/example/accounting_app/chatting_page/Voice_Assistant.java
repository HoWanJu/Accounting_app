package com.example.accounting_app.chatting_page;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;
import java.text.SimpleDateFormat;

import com.example.accounting_app.Category;
import com.example.accounting_app.Function;
import com.example.accounting_app.R;
import com.example.accounting_app.setting;
import com.google.cloud.dialogflow.v2beta1.DetectIntentResponse;
//import com.google.api.gax.core.FixedCredentialsProvider;
//import com.google.auth.oauth2.GoogleCredentials;
//import com.google.auth.oauth2.ServiceAccountCredentials;
//import com.google.cloud.dialogflow.v2beta1.QueryInput;
//import com.google.cloud.dialogflow.v2beta1.SessionName;
//import com.google.cloud.dialogflow.v2beta1.SessionsClient;
//import com.google.cloud.dialogflow.v2beta1.SessionsSettings;
//import com.google.cloud.dialogflow.v2beta1.TextInput;

//import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import ai.api.AIServiceContext;
import ai.api.AIServiceContextBuilder;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
//Listen import
import ai.api.AIListener;
import ai.api.model.AIError;
import ai.api.model.Result;
import ai.api.android.AIService;

import android.Manifest;
import android.content.pm.PackageManager;
public class Voice_Assistant extends AppCompatActivity implements AIListener {
    private ImageView sendBtn;
    private RelativeLayout mic;
    private LinearLayout ac_text;
    private Button switch_btn_chat;
    private Button switch_btn_account;
    private ImageView backBtn;
    private LinearLayout account_expense;
    private LinearLayout account_income;
    private ImageView closeBtn_expense;
    private ImageView closeBtn_income;
    private Button button_income_Expense;
    private Button button_expense_Income;
    private Button set;


    //chatbox
    private static final String TAG = Voice_Assistant.class.getSimpleName();
    private static final int USER = 10001;
    private static final int BOT = 10002;

    private String uuid = UUID.randomUUID().toString();
    private LinearLayout chatLayout;
    private EditText queryEditText;

    // Android client
    private AIRequest aiRequest;
    private AIDataService aiDataService;
    private AIServiceContext customAIServiceContext;

    // Java V2
//    private SessionsClient sessionsClient;
//    private SessionName session;
    //Listen
    private AIService aiService;
    ImageButton listenButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice__assistant);

        final ScrollView scrollview = findViewById(R.id.chatScrollView);
        scrollview.post(() -> scrollview.fullScroll(ScrollView.FOCUS_DOWN));

        chatLayout = findViewById(R.id.chatLayout);

        sendBtn = findViewById(R.id.sendBtn);
        sendBtn.setOnClickListener(this::sendMessage);

        queryEditText = findViewById(R.id.queryEditText);
        queryEditText.setOnKeyListener((view, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_DOWN) {
                switch (keyCode) {
                    case KeyEvent.KEYCODE_DPAD_CENTER:
                    case KeyEvent.KEYCODE_ENTER:
                        sendMessage(sendBtn);
                        return true;
                    default:
                        break;
                }
            }
            return false;
        });
        mic = findViewById(R.id.mic);

        account_expense = findViewById(R.id.account_expense);
        account_income = findViewById(R.id.account_income);
        closeBtn_expense = findViewById(R.id.closeBtn_expense);
        closeBtn_income = findViewById(R.id.closeBtn_income);

        //切換至記帳or聊天
        switch_btn_chat = findViewById(R.id.switch_btn_chat);
        switch_btn_account = findViewById(R.id.switch_btn_account);

        set = findViewById(R.id.button_set);

        backBtn = findViewById(R.id.backBtn);
        ac_text = findViewById(R.id.ac_text);

        button_income_Expense = findViewById(R.id.button_income_Expense);
        button_expense_Income = findViewById(R.id.button_expense_Income);


        account_expense.setVisibility(View.GONE);
        account_income.setVisibility(View.GONE);

        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Voice_Assistant.this, Function.class);
                startActivity(intent);
            }
        });


        //按鈕切換至聊天
        switch_btn_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ac_text.setVisibility(View.VISIBLE);
                switch_btn_chat.setVisibility(View.GONE);
                switch_btn_account.setVisibility(View.GONE);
                account_expense.setVisibility(View.GONE);
                account_income.setVisibility(View.GONE);
                backBtn.setVisibility(View.VISIBLE);
                mic.setVisibility(View.VISIBLE);
                sendBtn.setVisibility(View.VISIBLE);
            }
        });

        backBtn.setVisibility(View.GONE);
        ac_text.setVisibility(View.GONE);
        mic.setVisibility(View.GONE);
        sendBtn.setVisibility(View.GONE);


        //從聊天切回至原本功能

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backBtn.setVisibility(View.GONE);
                switch_btn_account.setVisibility(View.VISIBLE);
                switch_btn_chat.setVisibility(View.VISIBLE);
                ac_text.setVisibility(View.GONE);
                mic.setVisibility(View.GONE);
                sendBtn.setVisibility(View.GONE);
                account_expense.setVisibility(View.GONE);
            }
        });


        //按鈕切換至記帳
        switch_btn_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account_expense.setVisibility(View.VISIBLE);
                account_income.setVisibility(View.GONE);
                ac_text.setVisibility(View.GONE);
                switch_btn_chat.setVisibility(View.GONE);
                switch_btn_account.setVisibility(View.GONE);
                mic.setVisibility(View.GONE);
                sendBtn.setVisibility(View.GONE);
            }
        });


        //從記帳(支出)切回至原本功能

        closeBtn_expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backBtn.setVisibility(View.GONE);
                switch_btn_account.setVisibility(View.VISIBLE);
                switch_btn_chat.setVisibility(View.VISIBLE);
                ac_text.setVisibility(View.GONE);
                mic.setVisibility(View.GONE);
                sendBtn.setVisibility(View.GONE);
                account_expense.setVisibility(View.GONE);
                account_income.setVisibility(View.GONE);
            }
        });

        //從記帳(收入)切回至原本功能

        closeBtn_income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backBtn.setVisibility(View.GONE);
                switch_btn_account.setVisibility(View.VISIBLE);
                switch_btn_chat.setVisibility(View.VISIBLE);
                ac_text.setVisibility(View.GONE);
                mic.setVisibility(View.GONE);
                sendBtn.setVisibility(View.GONE);
                account_expense.setVisibility(View.GONE);
                account_income.setVisibility(View.GONE);
            }
        });


//        在記帳(支出)按下收入按鈕
        button_income_Expense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account_expense.setVisibility(View.GONE);
                account_income.setVisibility(View.VISIBLE);
                ac_text.setVisibility(View.GONE);
                switch_btn_chat.setVisibility(View.GONE);
                switch_btn_account.setVisibility(View.GONE);
                mic.setVisibility(View.GONE);
                sendBtn.setVisibility(View.GONE);
            }
        });


//        在記帳(收入)按下支出按鈕
        button_expense_Income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                account_expense.setVisibility(View.VISIBLE);
                account_income.setVisibility(View.GONE);
                ac_text.setVisibility(View.GONE);
                switch_btn_chat.setVisibility(View.GONE);
                switch_btn_account.setVisibility(View.GONE);
                mic.setVisibility(View.GONE);
                sendBtn.setVisibility(View.GONE);
            }
        });








        //權限
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest();
        }

//         Android client
        initChatbot();

        // Java V2
//        initV2Chatbot();
    }
    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                101);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {
                } else {
                }
                return;
            }
        }
    }

    public void listenButtonOnClick(final View view) {
        aiService.startListening();
    }


    private void initChatbot() {
        final AIConfiguration config = new AIConfiguration("f37ab19dce164cf68256557b4cb05129",
                AIConfiguration.SupportedLanguages.ChineseTaiwan,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(this);
        aiDataService = new AIDataService(Voice_Assistant.this, config);
        customAIServiceContext = AIServiceContextBuilder.buildFromSessionId(uuid);// helps to create new session whenever app restarts
        aiRequest = new AIRequest();
    }


    private void sendMessage(View view) {
        String msg = queryEditText.getText().toString();


        if (msg.trim().isEmpty()) {
            Toast.makeText(Voice_Assistant.this, "Please enter your query!", Toast.LENGTH_LONG).show();
        } else {
            showTextView(msg, USER);
            queryEditText.setText("");
//             Android client
            aiRequest.setQuery(msg);
            RequestTask requestTask = new RequestTask(Voice_Assistant.this, aiDataService, customAIServiceContext);
            requestTask.execute(aiRequest);

        }
    }

    public void callback(AIResponse aiResponse) {
        final Result result = aiResponse.getResult();
        if (result != null) {
            // process aiResponse here
            String botReply = aiResponse.getResult().getFulfillment().getSpeech();
            Log.d(TAG, "Bot Reply: " + botReply);
            showTextView(botReply, BOT);
        } else {
            Log.d(TAG, "Bot Reply: Null");
            showTextView("There was some communication issue. Please Try again!", BOT);
        }
    }

    public void callbackV2(DetectIntentResponse response) {
        if (response != null) {
            // process aiResponse here
            String botReply = response.getQueryResult().getFulfillmentText();
            Log.d(TAG, "V2 Bot Reply: " + botReply);
            showTextView(botReply, BOT);
        } else {
            Log.d(TAG, "Bot Reply: Null");
            showTextView("There was some communication issue. Please Try again!", BOT);
        }
    }

    private void showTextView(String message, int type) {
        FrameLayout layout;
        switch (type) {
            case USER:
                layout = getUserLayout();
                break;
            case BOT:
                layout = getBotLayout();
                break;
            default:
                layout = getBotLayout();
                break;
        }


        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");

        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間

        String str = formatter.format(curDate);


        layout.setFocusableInTouchMode(true);
        chatLayout.addView(layout); // move focus to text view to automatically make it scroll up if softfocus
        TextView tv = layout.findViewById(R.id.chatMsg);
        tv.setText(message);
        TextView time = layout.findViewById(R.id.text_message_time);
        time.setText(str);
        layout.requestFocus();
        queryEditText.requestFocus(); // change focus back to edit text to continue typing
    }

    FrameLayout getUserLayout() {
        LayoutInflater inflater = LayoutInflater.from(Voice_Assistant.this);
        return (FrameLayout) inflater.inflate(R.layout.user_msg_layout, null);
    }

    FrameLayout getBotLayout() {
        LayoutInflater inflater = LayoutInflater.from(Voice_Assistant.this);
        return (FrameLayout) inflater.inflate(R.layout.bot_msg_layout, null);
    }

    @Override
    public void onResult(final AIResponse response) {
        //listen result
        Log.d(TAG, response.toString());
        final Result result = response.getResult();
        showTextView(result.getResolvedQuery(), USER);
        //response
        final String botReply = result.getFulfillment().getSpeech();
        Log.d(TAG, "Bot Reply: " + botReply);
        showTextView(botReply, BOT);
    }

    @Override
    public void onError(final AIError error) {

    }

    @Override
    public void onAudioLevel(final float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }
}