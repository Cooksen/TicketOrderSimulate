package com.example.TickerOrder;

import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Date;
import androidx.appcompat.app.AppCompatActivity;


import java.text.SimpleDateFormat;


public class MainActivity extends AppCompatActivity {
    private static final int cal_REQUESTCODE = 1;

    private Spinner spinnerFrom;
    private Spinner spinnerEnd;
    private String nowDate="";
    private String fromStation, endStation, selectedSeatClassText,selectedSeatFavorText;
    private int i_normalTicket, i_childTicket, i_elderTicket, i_loveTicket, i_stuTicket;
    private String[] res;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerFrom = (Spinner) findViewById(R.id.spinnerFrom);
        spinnerEnd = (Spinner) findViewById(R.id.spinnerEnd);

        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this,
                        R.array.station,
                        android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFrom.setAdapter(adapter);
        spinnerFrom.setSelection(1, false);
        spinnerEnd.setAdapter(adapter);
        spinnerEnd.setSelection(11, false);


        Button btn_getDate = findViewById(R.id.btn_getDate);
        btn_getDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentDateSet = new Intent(MainActivity.this, calendar.class);
                startActivityForResult(intentDateSet, cal_REQUESTCODE);
            }
        });

        RadioButton bis = findViewById(R.id.radioBis);
        bis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i_childTicket = i_elderTicket = i_loveTicket = i_stuTicket = 0;
                EditText t1 = findViewById(R.id.childTicket);
                t1.setVisibility(View.INVISIBLE);
                t1.setTextColor(Color.rgb(255, 255, 255));

                EditText t2 = findViewById(R.id.loveTicket);
                t2.setVisibility(View.INVISIBLE);
                t2.setTextColor(Color.rgb(255, 255, 255));

                EditText t3 = findViewById(R.id.elderTicket);
                t3.setVisibility(View.INVISIBLE);
                t3.setTextColor(Color.rgb(255, 255, 255));

                EditText t4 = findViewById(R.id.stuTicket);
                t4.setVisibility(View.INVISIBLE);
                t4.setTextColor(Color.rgb(255, 255, 255));
            }
        });

        RadioButton std = findViewById(R.id.radioStd);
        std.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText t1 = findViewById(R.id.childTicket);
                t1.setVisibility(View.VISIBLE);
                t1.setTextColor(Color.rgb(0, 0, 0));

                EditText t2 = findViewById(R.id.loveTicket);
                t2.setVisibility(View.VISIBLE);
                t2.setTextColor(Color.rgb(0, 0, 0));

                EditText t3 = findViewById(R.id.elderTicket);
                t3.setVisibility(View.VISIBLE);
                t3.setTextColor(Color.rgb(0, 0, 0));

                EditText t4 = findViewById(R.id.stuTicket);
                t4.setVisibility(View.VISIBLE);
                t4.setTextColor(Color.rgb(0, 0, 0));
            }
        });


        Button btn_sendSearch = findViewById(R.id.btn_sendSearch);
        btn_sendSearch.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
//        得到起訖站
                fromStation = spinnerFrom.getSelectedItem().toString();
                endStation = spinnerEnd.getSelectedItem().toString();

//        得到車廂種類 也判斷他有沒有選
                RadioGroup seatClass = findViewById(R.id.seatClass);
                int selectedSeatClassId = seatClass.getCheckedRadioButtonId();
                if (selectedSeatClassId != -1){
                    RadioButton selectedSeatClass = findViewById(selectedSeatClassId);
                    selectedSeatClassText = selectedSeatClass.getText().toString();
                }else{
                    Toast.makeText(
                            MainActivity.this,
                            "請選擇車廂種類",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

//        得到票種數量

                EditText normalTicket = findViewById(R.id.normalTicket);
                if(!exist(normalTicket.getText().toString())){
                i_normalTicket = 0;
                }else {i_normalTicket = Integer.parseInt(normalTicket.getText().toString());}

                EditText childTicket = findViewById(R.id.childTicket);
                if(!exist(childTicket.getText().toString())){
                    i_childTicket = 0;
                }else {i_childTicket = Integer.parseInt(childTicket.getText().toString());}

                EditText elderTicket = findViewById(R.id.elderTicket);
                if(!exist(elderTicket.getText().toString())){
                    i_elderTicket = 0;
                }else {i_elderTicket = Integer.parseInt(elderTicket.getText().toString());}

                EditText loveTicket = findViewById(R.id.loveTicket);
                if(!exist(loveTicket.getText().toString())){
                    i_loveTicket = 0;
                }else {i_loveTicket = Integer.parseInt(loveTicket.getText().toString());}

                EditText stuTicket = findViewById(R.id.stuTicket);
                if(!exist(stuTicket.getText().toString())){
                    i_stuTicket = 0;
                }else {i_stuTicket = Integer.parseInt(stuTicket.getText().toString());}


                if (i_childTicket + i_elderTicket + i_loveTicket + i_normalTicket + i_stuTicket == 0){
                    Toast.makeText(
                            MainActivity.this,
                            "至少輸入一張車票種類",
                            Toast.LENGTH_SHORT).show();
                    return;
                }else if (i_childTicket + i_elderTicket + i_loveTicket + i_normalTicket + i_stuTicket < 0){
                    Toast.makeText(
                            MainActivity.this,
                            "無效的車票數量",
                            Toast.LENGTH_SHORT).show();
                    return;
                }

//        得到座位偏好
                RadioGroup seatFavor = findViewById(R.id.seatFavor);
                int selectedSeatFavorId = seatFavor.getCheckedRadioButtonId();
                if (selectedSeatFavorId != -1){
                    RadioButton selectedSeatFavor = findViewById(selectedSeatFavorId);
                    selectedSeatFavorText = selectedSeatFavor.getText().toString();
                }else{
                    Toast.makeText(
                            MainActivity.this,
                            "請選擇座位偏好",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if(nowDate.isEmpty()){
                    nowDate = new SimpleDateFormat("yyyy/MM/dd-HH:mm").format(new Date());
                }
                String infoMessage = ("啟程站 : " + fromStation + "  到達站 : " + endStation  + "\n" +
                        "乘車時間 : " + nowDate + "\n" +
                        "車廂種類 : " + selectedSeatClassText + "\n" +
                        "全票" + i_normalTicket + "張 " +
                        "孩童" + i_childTicket + "張 " +
                        "敬老" + i_elderTicket + "張 " +
                        "愛心" + i_loveTicket + "張 " +
                        "大學生" + i_stuTicket + "張" + "\n" +
                        "座位偏好 : " + selectedSeatFavorText);
                String passMsg =(
                        fromStation + " " + endStation + " " + nowDate + " " + selectedSeatClassText + " " + i_normalTicket + " " + i_childTicket + " "
                        + i_elderTicket + " " + i_loveTicket + " " + i_stuTicket + " " + selectedSeatFavorText
                        );
//                Toast.makeText(
//                        MainActivity.this,
//                        infoMessage,
//                        Toast.LENGTH_SHORT).show();
                System.out.println(passMsg);
                Intent intent = new Intent(MainActivity.this, com.example.TickerOrder.background.loading.class);
                intent.putExtra("info", passMsg);
                startActivity(intent);
            }
        });



        Button btn_searchTicket = findViewById(R.id.btn_searchTicket);
        btn_searchTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSearchTicket = new Intent();
                intentSearchTicket.setClass(MainActivity.this, searchTicket.class);
                startActivity(intentSearchTicket);
            }
        });



    }

    public boolean exist(String x){
        if(!x.isEmpty()){
            return true;
        }
            return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case cal_REQUESTCODE:
                nowDate = data.getStringExtra("dateTime");
                res = nowDate.split("/| |:");
//                Toast.makeText(
//                        MainActivity.this,
//                        nowDate,
//                        Toast.LENGTH_SHORT).show();
                break;

        }
    }
}
