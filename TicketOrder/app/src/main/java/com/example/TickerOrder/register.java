package com.example.TickerOrder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.TickerOrder.dbcode.Account;

import androidx.appcompat.app.AppCompatActivity;

public class register extends AppCompatActivity {

    private String userName;
    private String ID;
    private String phoneNum;
    private String password;
    private String password1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button btn_register = findViewById(R.id.makesure);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText t1 = findViewById(R.id.password);
                password = t1.getText().toString();

                EditText t2 = findViewById(R.id.phoneNum);
                phoneNum = t2.getText().toString();

                EditText t3 = findViewById(R.id.IDCard);
                ID = t3.getText().toString();

                EditText t4 = findViewById(R.id.Name);
                userName = t4.getText().toString();

                EditText t5 = findViewById(R.id.repassword);
                password1 = t5.getText().toString();

                if(!password.equals(password1)){
                    Toast.makeText(register.this, "兩次密碼不符", Toast.LENGTH_SHORT).show();
                }else{
                    creatAcc creatacc = new creatAcc();
                    creatacc.execute("");
                }

            }
        });

        Button goback = findViewById(R.id.button);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    private class creatAcc extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            Toast.makeText(register.this, "註冊中...", Toast.LENGTH_SHORT)
//                    .show();
        }

        @Override
        protected String doInBackground(String... params) {

                return String.format("%d", Account.CreateAccount(userName,ID,phoneNum,password));
        }

        @Override
        protected void onPostExecute(String result) {
            switch (result){
                case "1":
                    Toast.makeText(register.this, "使用者名稱請用中文名稱", Toast.LENGTH_SHORT).show();

                    break;
                case "2":
                    Toast.makeText(register.this, "身分證字號資訊已存在或有誤", Toast.LENGTH_SHORT).show();

                    break;
                case "3":
                    Toast.makeText(register.this, "手機號碼資訊已存在或有誤", Toast.LENGTH_SHORT).show();

                    break;
                case "4":
                    Toast.makeText(register.this, "密碼長度不足(至少六碼)", Toast.LENGTH_SHORT).show();

                    break;
                case "5":
                    Toast.makeText(register.this, "此帳號資訊已經存在", Toast.LENGTH_SHORT).show();

                    break;
                case "6":
                    Toast.makeText(register.this, "註冊成功", Toast.LENGTH_SHORT).show();

                    finish();

            }


        }
    }
}
