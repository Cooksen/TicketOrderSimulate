package com.example.TickerOrder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.TickerOrder.dbcode.*;

import java.util.ArrayList;

public class ticketCheck extends AppCompatActivity {
        private String ID;
        LinearLayout layoutlist;
        LayoutInflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        ID = intent.getStringExtra("ID");

        setContentView(R.layout.activity_ticket_check);
        Button back = findViewById(R.id.goback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(ticketCheck.this, MainActivity.class);
                startActivity(intent);
            }
        });
        inflater = LayoutInflater.from(ticketCheck.this);
        layoutlist = findViewById(R.id.ticketCheckLayout);
//      addAidlog();
        new GetRecord().execute(ID);
    }

//    得到車票紀錄
    private class GetRecord extends AsyncTask<String , Void , ArrayList<String>> {

        private ProgressDialog progressBar;
        //進度條元件
        @Override
        protected void onPreExecute() {
            //執行前 設定可以在這邊設定
            super.onPreExecute();

            progressBar = new ProgressDialog(ticketCheck.this);
            progressBar.setMessage("Loading record...");
            progressBar.setCancelable(false);
            progressBar.show();
            //初始化進度條並設定樣式及顯示的資訊。
        }

        @Override
        protected ArrayList<String> doInBackground(String... ID) {
            String Id = ID[0];
            return getRecord.getRecords(Id);
        }

        @Override
        protected void onPostExecute(ArrayList<String> as) {
            //執行後 完成背景任務
            super.onPostExecute(as);
            for(int i = 0; i < as.size(); i++){
                addView(as.get(i));
            }
            progressBar.dismiss();
        }


    }

    private void addView(String info) {
        String[] record = new String[10];
        record = info.split(" ");

        View carAppend = getLayoutInflater().inflate(R.layout.check_card, null, false);
        TextView startTime = carAppend.findViewById(R.id.startTime);
        TextView endTime = carAppend.findViewById(R.id.endTime);
        TextView carNum = carAppend.findViewById(R.id.carNum);

        TextView go = carAppend.findViewById(R.id.go);
        TextView needTime = carAppend.findViewById(R.id.needTime);
        Button appCompatButton = carAppend.findViewById(R.id.appCompatButton);

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                delete dl = new delete();
//                dl.execute(info);
                btn_in_checkcard(info);
            }
        });

        View divider = carAppend.findViewById(R.id.divider);

        TextView seatId = carAppend.findViewById(R.id.seatId);
        TextView discountText = carAppend.findViewById(R.id.discountText);
        TextView endStation = carAppend.findViewById(R.id.endStation);
        TextView startStation = carAppend.findViewById(R.id.startStation);
        TextView date = carAppend.findViewById(R.id.date);

        String[] s = record[5].split(":");
        String[] e = record[6].split(":");
        int need_time_hour = Integer.parseInt(e[0]) - Integer.parseInt(s[0]);
        int need_time_min = Integer.parseInt(e[1]) - Integer.parseInt(s[1]);
        if (need_time_min < 0){
            need_time_hour -= 1;
            need_time_min += 60;
        }
        String need_time = need_time_hour + "小時" + need_time_min +"分";

        startTime.setText(record[5]);
        endTime.setText(record[6]);
        needTime.setText(need_time);
        carNum.setText(record[1]);
        seatId.setText(record[7]+"車"+record[8]);
//        加入卡片資訊
        discountText.setText(record[9]);
        endStation.setText(intToSta(Integer.parseInt(record[3])));
        startStation.setText(intToSta(Integer.parseInt(record[2])));
        date.setText(record[4]);
        layoutlist.addView(carAppend);
    }

    private class delete extends AsyncTask<String , Void , String>{

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(String... strings) {
            btn_in_checkcard(strings[0]);

            return null;
        }

        @Override
        protected void onPostExecute(String result){

        }
    }



//    需要執行續
    private void btn_in_checkcard(String info){
        String[] record = info.split(" ");

        new AlertDialog.Builder(ticketCheck.this)
                .setTitle("修改票卷狀態")
                .setPositiveButton("退票", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Date date = new Date(record[4]);
                        Time s = new Time(record[5]);
                        Time e = new Time(record[6]);
                        Intent intent = new Intent();
                        intent.setClass(ticketCheck.this, deleting.class);
                        intent.putExtra("info",info);
                        startActivity(intent);

                    }
                }).setNegativeButton("刪除",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        Date date = new Date(record[4]);
                        Time s = new Time(record[5]);
                        Time e = new Time(record[6]);
                        Intent intent = new Intent();
                        intent.setClass(ticketCheck.this, delederec.class);
                        intent.putExtra("info",info);
                        startActivity(intent);
            }


        }).setNeutralButton("取消",(dialog, which) -> {

        })
                .show();
    }

    public static String intToSta(int x){
        switch (x){
            case 1:
                return "南港";
            case 2:
                return "台北";
            case 3:
                return "板橋";
            case 4:
                return "桃園";
            case 5:
                return "新竹";
            case 6:
                return "苗栗";
            case 7:
                return "台中";
            case 8:
                return "彰化";
            case 9:
                return "雲林";
            case 10:
                return "嘉義";
            case 11:
                return "台南";
            case 12:
                return "左營";
            default:
                return " ";
        }
    }

}