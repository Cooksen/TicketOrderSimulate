package com.example.TickerOrder.background;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.TickerOrder.R;
import com.example.TickerOrder.dbcode.Rail;
import com.example.TickerOrder.dbcode.Record;
import com.example.TickerOrder.searchCar;

import java.text.SimpleDateFormat;
import java.util.Date;


//0fromStation, 1endStation, 2Date, 3selectedSeatClassText, + 4i_normalTicket, + 5i_childTicket,
//6i_elderTicket, 7i_loveTicket, 8i_stuTicket, 9selectedSeatFavorText

public class loading extends AppCompatActivity {

    public String timeCount(String s, String e, String dep, int type){
        int start = StaToInt(s);
        int end = StaToInt(e);

        //dir = 1北上
        //dir = 0南下
        String[] tmp = dep.split("/|-|:");
        //2021/6/15-9:30
        int[] intdep=new int[tmp.length];
        for(int i = 0; i < tmp.length; i++){
            intdep[i] = Integer.parseInt(tmp[i]);
        }

//        int[] ty1 = {0,13,18,13,10,12,20,13,12,12,10,8};
        int[] ty1 = {0, 8,10,12,12,13,20,12,10,13,18,13};


                if(start>end){

                    for(int i = start; i <12  ; i++){
                        intdep[4] -= ty1[i];
                        if(intdep[4]<0){
                            intdep[3]--;
                            intdep[4]+=60;
                        }
                    }
                }
                else{

                for(int i = start-1; i >= 0 ; i--){
                    intdep[4] -= ty1[i];
                    if(intdep[4]<0){
                        intdep[3]--;
                        intdep[4]+=60;
                    }
                }}

        String res=String.format(intdep[0]+"/"+intdep[1]+"/"+intdep[2]+"-"+intdep[3]+":"+intdep[4]);
        return res;
    }

    String info;
    int year, month, day, hour, minute;
    int now_year, now_month, now_day, now_hour, now_minute, num;
    int state = 0;
    String s, e;
    boolean univ = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        String nowDate = new SimpleDateFormat("yyyy/MM/dd-HH:mm").format(new Date());
        Intent intent = getIntent();
        String[] info = intent.getStringExtra("info").split(" ");
        //2021/6/15-9:30

        String tmp1[] = nowDate.split("/|:|-");
        now_year = Integer.parseInt(tmp1[0]);
        now_month = Integer.parseInt(tmp1[1]);
        now_day = Integer.parseInt(tmp1[2]);
        now_hour = Integer.parseInt(tmp1[3]);
        now_minute = Integer.parseInt(tmp1[4]);

        if(info[3].equals("標準車廂")){
            if(info[9].equals("無偏好")){
                state = 0;
            }else if(info[9].equals("靠窗優先")){
                state = 1;
            }else{
                state = 2;
            }
        }else{
            if(info[9].equals("無偏好")){
                state = 3;
            }else if(info[9].equals("靠窗優先")){
                state = 4;
            }else{
                state = 5;
            }
        }

        num = Integer.parseInt(info[4]) + Integer.parseInt(info[5]) + Integer.parseInt(info[6]) + Integer.parseInt(info[7]) +Integer.parseInt(info[8]);
        s = info[0];
        e = info[1];

        String tmp[] = timeCount(info[0],info[1],info[2],1).split("/|:|-");
        year = Integer.parseInt(tmp[0]);
        month = Integer.parseInt(tmp[1]);
        day = Integer.parseInt(tmp[2]);
        hour = Integer.parseInt(tmp[3]);
        minute = Integer.parseInt(tmp[4]);

        if(Integer.parseInt(info[8])!=0){
            univ = true;
        }

        System.out.println(year+" "+month+" "+day+" "+hour+" "+minute+" "+now_year
                +" "+now_month+" "+now_day+" "+now_hour+" "+now_minute+" "+s+" "+e
                +" "+num+" "+state+" "+univ);
        Search search = new Search();
        search.execute("");

    }

    private class Search extends AsyncTask<String, Void, String> {
        String res = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Toast.makeText(loading.this, "載入中...", Toast.LENGTH_SHORT)
                    .show();
        }

        @Override
        protected String doInBackground(String... params) {
            Record a = new Record(num, state, s, e, year, month, day, hour, minute);
            Rail[] rail;

            rail = a.Search(year, month, day, hour,
                    minute, now_year, now_month, now_day, now_hour, now_minute, s, e, num, state, univ);

            info = a.getInfo();
            System.out.println(a.getInfo());
            return a.getInfo();
        }

        @Override
        protected void onPostExecute(String result) {
//            Toast.makeText(loading.this, result, Toast.LENGTH_SHORT)
//                    .show();
            Intent intent = new Intent();
            intent.setClass(loading.this, searchCar.class);
            Bundle bundle = new Bundle();
            bundle.putString("StartEnd", s+" "+e);
            bundle.putString("info",info);
            int uni=0;
            if(univ){
                uni = 1;
            }
            bundle.putString("info2",num+" "+state+" "+ String.format("%d",uni));
            intent.putExtras(bundle);
            startActivity(intent);

            }
        }
        public int StaToInt(String sta){
        int end = 0;
            switch (sta){
                case "南港":
                    end = 1;
                    break;
                case "台北":
                    end = 2;
                    break;
                case "板橋":
                    end = 3;
                    break;
                case "桃園":
                    end = 4;
                    break;
                case "新竹":
                    end = 5;
                    break;
                case "苗栗":
                    end = 6;
                    break;
                case "台中":
                    end = 7;
                    break;
                case "彰化":
                    end = 8;
                    break;
                case "雲林":
                    end = 9;
                    break;
                case "嘉義":
                    end = 10;
                    break;
                case "台南":
                    end = 11;
                    break;
                case "左營":
                    end = 12;
                    break;
                default:
                    break;
            }
        return end;
        }
    }

