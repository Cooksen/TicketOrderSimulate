package com.example.TickerOrder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.TickerOrder.dbcode.Account;

import androidx.appcompat.app.AppCompatActivity;

public class login extends AppCompatActivity {
    private String accountTag;
    private String phone;
    private String IDCard;
    private String passward;
    private String rail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        rail = intent.getStringExtra("rail");

        Button btn_login = findViewById(R.id.login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText id = findViewById(R.id.IDCard);
                EditText phone = findViewById(R.id.password);
                IDCard = id.getText().toString();
                passward = phone.getText().toString();
                if(!IDCard.isEmpty()&&!passward.isEmpty()){
                    account acc = new account();
                    acc.execute("");
                }else{
                    Toast.makeText(login.this, "輸入不完整",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btn_register = findViewById(R.id.register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, register.class);
                startActivity(intent);
            }
        });

        Button btn_back = findViewById(R.id.goback);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private class account extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(login.this, "登入中...", Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        protected String doInBackground(String... params) {
            String[] tmp =Account.login(IDCard,passward).split(" ");

            if(tmp[0].equals("1")){
                rail = rail +" "+tmp[1]+" "+tmp[2]+" "+tmp[3];
                //名字 手機 身分證
                return "1";
            }else {
                return "2";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            switch (result){
                case "1":
                    Toast.makeText(login.this, "登入成功", Toast.LENGTH_SHORT).show();
                    Intent intent1 = new Intent(login.this, checkbook.class);
                    intent1.putExtra("rail", rail);
                    startActivity(intent1);
                    break;
                default:
                    Toast.makeText(login.this, "帳號或密碼錯誤，請再試一次", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(login.this, login.class);
                    intent2.putExtra("rail", rail);
                    startActivity(intent2);
                    break;

            }

        }
    }





}
