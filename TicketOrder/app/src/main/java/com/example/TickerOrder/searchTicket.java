package com.example.TickerOrder;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class searchTicket extends AppCompatActivity {


    EditText IDCardText;
    String IDCard;
    Button btnFetch,btnClear, Clearout;
    TextView txtData;
    private String[] stations= {
            "左營","台南","嘉義","雲林","彰化","台中","苗栗","新竹","桃園","板橋","台北","南港"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ticket);
        IDCardText = (EditText)findViewById(R.id.ID);
        txtData = (TextView) this.findViewById(R.id.txtData);
        btnFetch = (Button) findViewById(R.id.btnFetch);
        btnClear = (Button) findViewById(R.id.btnClear);
        Clearout = (Button) findViewById(R.id.clearout);

        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                IDCard = IDCardText.getText().toString();
                Intent intent = new Intent();
                intent.setClass(searchTicket.this,ticketCheck.class );
                intent.putExtra("ID",IDCard);
                startActivity(intent);

//                ConnectMySql connectMySql = new ConnectMySql();
//                connectMySql.execute("");
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Clearout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConClear con = new ConClear();
                con.execute("");
            }
        });
    }

    private class ConnectMySql extends AsyncTask<String, Void, String> {
        String res = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(searchTicket.this, "載入中...", Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "senchao");
                System.out.println("Databaseection success");

                String result = "您的訂票紀錄為：\n\n";
                String sql = "SELECT * FROM record WHERE IDCard = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1,IDCard);
                ResultSet rs = st.executeQuery();
                int i = 0;
                while (rs.next()) {
                    String car_id = rs.getString("car_id");
                    int start = rs.getInt("start");
                    int end = rs.getInt("end");
                    String date = rs.getString("date");
                    String Dep = rs.getString("DepTime");
                    String Ari = rs.getString("AriTime");
                    String car_num = rs.getString("car_num");
                    String seat = rs.getString("seat");
                    result += "車次：" + car_id + " 起站: " + stations[start-1] +" 迄站: "+ stations[end-1] +"\n出發時間: " +
                            date + "/" + Dep + "\n抵達時間:" + date + "/" + Ari + "\n" + car_num +"車廂 " +
                            seat + "\n" + "\n";
                    i++;
                }
                if(i==0){
                    result="查無資料";
                }
                res = result;

            } catch (Exception e) {
                e.printStackTrace();
                res = e.toString();
            }
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            txtData.setText(result);
            if(result.equalsIgnoreCase("查無資料")){
                txtData.setGravity(Gravity.CENTER_HORIZONTAL);
                Clearout.setVisibility(View.INVISIBLE);
                txtData.setTextSize(20);
            }
            else{
                txtData.setGravity(Gravity.LEFT);
                txtData.setTextSize(18);
                IDCardText.setVisibility(View.INVISIBLE);
                btnFetch.setVisibility(View.INVISIBLE);
                Clearout.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    private class ConClear extends AsyncTask<String, Void, String> {
        String result = "刪除成功";
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(searchTicket.this, "載入中...", Toast.LENGTH_SHORT)
                    .show();
        }
        @Override
        protected String doInBackground(String... params) {

            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://kaycloud.i234.me:3306/sen_oop?useSSL=false", "senchao", "senchao");
                String sql = "DELETE FROM record WHERE IDCard = ?";
                PreparedStatement st = con.prepareStatement(sql);
                st.setString(1,IDCard);
                st.executeUpdate();
                return result;
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                result = "刪除失敗";
                return result;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPreExecute();
            txtData.setText(result);
            Clearout.setVisibility(View.INVISIBLE);
            Toast.makeText(searchTicket.this, "刪除中...", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}