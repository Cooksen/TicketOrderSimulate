package com.example.TickerOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.TickerOrder.background.loading;

import org.jetbrains.annotations.NotNull;


public class searchCar extends AppCompatActivity {

    LinearLayout layoutlist;
    public String depTimeCount(String s, String e, @NotNull String depTime, int id){
        loading ld = new loading();
        int start=ld.StaToInt(s)-1;
        int end = ld.StaToInt(e)-1;
        System.out.println(depTime+"in deptimecount");
//        int[] ty1 = {0,13,18,13,10,12,20,13,12,12,10,8};
        int[] ty1 = {0, 8,10,12,12,13,20,12,10,13,18,13};
//        int[] ty2 = {0, 0, 0, 0, 0,44, 0, 0, 0,42,11,8};
        int[] ty3 = {0, 8,11,42, 0, 0, 0,44, 0, 0, 0, };
//        int[] ty3 = {0, 0, 0, 0, 0, 0,20,12,12,12,10,8};
        int[] ty2 = {0, 8,10,12,12,12,20, 0, 0, 0, 0, 0};

        String[] tmp = depTime.split(":");
        //9:30
        int[] intdep=new int[tmp.length];
        for(int i = 0; i < tmp.length; i++){
            intdep[i] = Integer.parseInt(tmp[i]);
        }

        if (id < 500) {
            //北上
            if(end<start){
        for(int i = 11; i > start ; i--){
          intdep[1]+=ty1[i];
          if(intdep[1]>60){
              intdep[0]++;
              intdep[1]-=60;

          }
        }
            }
            else{
                for(int j = 0; j <= start; j++){
                    intdep[1]+=ty1[j];
                    if(intdep[1]>60){
                        intdep[0]++;
                        intdep[1]-=60;
                    }
                }
            }

        }else if(id<1000){
            if(end<start){
                for(int i = 6; i > start ; i--){
                    intdep[1]+=ty2[i];
                    if(intdep[1]>60){
                        intdep[0]++;
                        intdep[1]-=60;
                    }
                }}
            else{
                for(int j = 0; j <= start; j++){
                    intdep[1]+=ty2[j];
                    if(intdep[1]>60){
                        intdep[0]++;
                        intdep[1]-=60;
                    }
                }
            }
        }else{
            if(end<start){
                for(int i = 11; i > start ; i--){
                    intdep[1]+=ty3[i];
                    if(intdep[1]>60){
                        intdep[0]++;
                        intdep[1]-=60;
                    }
                }}
            else{
                for(int j = 0; j <= start; j++){
                    intdep[1]+=ty3[j];
                    if(intdep[1]>60){
                        intdep[0]++;
                        intdep[1]-=60;
                    }
                }
            }
        }
        return String.format("%02d:%02d",intdep[0],intdep[1]);
    }

    public String arrTimeCount(String e, String depTime, int id){
        loading ld = new loading();
        int end = ld.StaToInt(e)-1;

//        int[] ty1 = {0,13,18,13,10,12,20,13,12,12,10,8};
        int[] ty1 = {0, 8,10,12,12,13,20,12,10,13,18,13};
//        int[] ty2 = {0, 0, 0, 0, 0,44, 0, 0, 0,42,11,8};
        int[] ty3 = {0, 8,11,42, 0, 0, 0,44, 0, 0, 0, 0};
//        int[] ty3 = {0, 0, 0, 0, 0, 0,20,12,12,12,10,8};
        int[] ty2 = {0, 8,10,12,12,12,20, 0, 0, 0, 0, 0};

        String[] tmp = depTime.split(":");
        //9:30
        int[] intdep=new int[tmp.length];
        for(int i = 0; i < tmp.length; i++){
            intdep[i] = Integer.parseInt(tmp[i]);
        }

        if (id < 500) {
            //北上
            if(id%2!=0){
                for(int i = 11; i >= end; i--){
                    intdep[1]+=ty1[i];
                    if(intdep[1]>60){
                        intdep[0]++;
                        intdep[1]-=60;
                    }
                }}
            else{
                for(int j = 0; j <= end; j++){
                    intdep[1]+=ty1[j];
                    if(intdep[1]>60){
                        intdep[0]++;
                        intdep[1]-=60;
                    }
                }
            }

        }else if(id<1000){
            if(id%2!=0){
                for(int i = 6; i >= end; i--){
                    intdep[1]+=ty2[i];
                    if(intdep[1]>60){
                        intdep[0]++;
                        intdep[1]-=60;
                    }
                }}
            else{
                for(int j = 0; j <= end; j++){
                    intdep[1]+=ty2[j];
                    if(intdep[1]>60){
                        intdep[0]++;
                        intdep[1]-=60;
                    }
                }
            }
        }else{
            if(id%2!=0){
                for(int i = 11; i >= end; i--){
                    intdep[1]+=ty3[i];
                    if(intdep[1]>60){
                        intdep[0]++;
                        intdep[1]-=60;
                    }
                }}
            else{
                for(int j = 0; j <= end; j++){
                    intdep[1]+=ty3[j];
                    if(intdep[1]>60){
                        intdep[0]++;
                        intdep[1]-=60;
                    }
                }
            }
        }
    return String.format("%02d:%02d",intdep[0],intdep[1]);
    }

    public String timeCount(String arrT, String depT){

        String[] tmp = arrT.split(":");
        //9:30
        int[] intarr=new int[tmp.length];
        for(int i = 0; i < tmp.length; i++){
            intarr[i] = Integer.parseInt(tmp[i]);
        }
        String[] tmp1 = depT.split(":");
        //9:30
        int[] intdep=new int[tmp1.length];
        for(int i = 0; i < tmp1.length; i++){
            intdep[i] = Integer.parseInt(tmp1[i]);
        }

        int min = -intarr[1] - intarr[0]*60 + intdep[1] + intdep[0]*60;
        if(min > 60){
            return String.format("%d小時%d分",min/60,min%60);
        }
        else{
        return String.format("%d分鐘",min);}
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_car);
        Intent intent = getIntent();
        //depTimeCount(se[0],se[1],tmp[2],Integer.parseInt(tmp[0])) 出發時間
        //1006 2021-06-15 16:30 一般票/112 2021-06-15 18:00 一般票/
        String[] info = intent.getStringExtra("info").split("/");
        //1006 2021-06-15 16:30 一般票
        String[] se = intent.getStringExtra("StartEnd").split(" ");
        //s[0] 出發 s[1]終點
        String[] info2 = intent.getStringExtra("info2").split(" ");
        //info[0] num [1]state [2]uni
        //System.out.println(info[2]);
        int len = info.length;
        String[] carInfo = new String[len];

        for(int i=0; i<info.length; i++){
            String[] tmp = info[i].split(" ");
            System.out.println(tmp[2]);
            //起站 終站 出發時間 抵達時間 車次 優惠 日期 需要時間 座位總數 偏好 是否大學
            //起站 到站 離站時間 到站時間 座位總數 座位偏好 車次 是否為大學生 到站日期
            carInfo[i] = se[0]+" "+se[1]+" "+depTimeCount(se[0],se[1],tmp[2],Integer.parseInt(tmp[0]))
            + " " + arrTimeCount(se[1],tmp[2],Integer.parseInt(tmp[0])) +
                    " "+ tmp[0] +" "+tmp[3] + " " +tmp[1]+" "+
                    timeCount(depTimeCount(se[0],se[1],tmp[2],Integer.parseInt(tmp[0])),
                            arrTimeCount(se[1],tmp[2],Integer.parseInt(tmp[0])))+" "+info2[0]+" "+info2[1]+" "+info2[2];
        }


        layoutlist = findViewById(R.id.layout_list);

        for(int i = 0; i < carInfo.length; i++){
            addView(carInfo[i]);
        }

        Button jump = findViewById(R.id.jump);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(searchCar.this, MainActivity.class);
                startActivity(intent);
            }
        });
        }

    private void createLayoutDynamically(int n) {

        for (int i = 0; i < n; i++) {
            Button myButton = new Button(this);
            myButton.setText("Button :"+i);
            myButton.setId(i);
            final int id_ = myButton.getId();

            LinearLayout layout = (LinearLayout) findViewById(R.id.layout_list);
            layout.addView(myButton);

            myButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {

                }
            });
        }}

    private void addView(String oneCarInfo) {
        String[] dividedInfo = oneCarInfo.split(" ");
        View carAppend = getLayoutInflater().inflate(R.layout.available_car, null, false);
        TextView startTime = carAppend.findViewById(R.id.startTime);
        TextView endTime = carAppend.findViewById(R.id.endTime);
        TextView carNum = carAppend.findViewById(R.id.carNum);

        TextView go = carAppend.findViewById(R.id.go);
        TextView needTime = carAppend.findViewById(R.id.needTime);
        Button appCompatButton = carAppend.findViewById(R.id.appCompatButton);

        appCompatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //起站 終站 出發時間 抵達時間 車次 優惠 日期 需要時間 座位總數 偏好 是否大學
                //起站 到站 離站時間 到站時間 座位總數 座位偏好 車次 是否為大學生 到站日期
                String kelvin = dividedInfo[0]+" "+dividedInfo[1]+" "+dividedInfo[2]+" "+dividedInfo[3]+" "+
                        dividedInfo[8]+" "+dividedInfo[9]+" "+dividedInfo[4]+" "+dividedInfo[10]+" "+
                        dividedInfo[6];
                Intent intent = new Intent(searchCar.this, login.class);
                intent.putExtra("rail", kelvin);
                startActivity(intent);
            }
        });

        View divider = carAppend.findViewById(R.id.divider);

        TextView seatId = carAppend.findViewById(R.id.seatId);
        TextView discountText = carAppend.findViewById(R.id.discountText);
        TextView endStation = carAppend.findViewById(R.id.endStation);
        TextView startStation = carAppend.findViewById(R.id.startStation);


        //dividedInfo 起站 終站 出發時間 抵達時間 車次 優惠 日期 需要時間 座位總數 偏好 是否大學

        startTime.setText(dividedInfo[2]);
        endTime.setText(dividedInfo[3]);
        carNum.setText("車次: "+dividedInfo[4]);
        startStation.setText(dividedInfo[0]);
        endStation.setText(dividedInfo[1]);
        discountText.setText(dividedInfo[5]);
        needTime.setText(dividedInfo[6]);
        seatId.setText(dividedInfo[7]);

        layoutlist.addView(carAppend);
        }
    }
