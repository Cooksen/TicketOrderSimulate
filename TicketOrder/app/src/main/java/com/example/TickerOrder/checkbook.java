package com.example.TickerOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.TickerOrder.background.ending;
import com.example.TickerOrder.dbcode.Account;
import com.example.TickerOrder.dbcode.Rail;
import com.example.TickerOrder.dbcode.Ticket;

public class checkbook extends AppCompatActivity {

    LinearLayout layoutlist;
    private String[] seatInfo = new String[5];
    Account account;
    Ticket[] tickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkbook);

        Intent intent = this.getIntent();
        //取得傳遞過來的資料
        String rail = intent.getStringExtra("rail");
        String[] rail_info = rail.split(" ");
//        rail_info 有九項 分別是 起站 到站 離站時間 到站時間 座位總數 座位偏好 車次 是否為大學生 到站日期
//                              名字 手機 身分證
        Rail rail_ = new Rail(rail_info[6], rail_info[8], rail_info[2], rail_info[3], rail_info[0],
                rail_info[1], rail_info[4], rail_info[5], rail_info[7], rail_info[11]);

        TextView bookId = findViewById(R.id.bookId);
        bookId.setText(rail_info[11]);

        TextView carType = findViewById(R.id.carType);
        carType.setText(Integer.parseInt(rail_info[5]) > 2 ? "商務車廂":"一般車廂");

        TextView seatNum = findViewById(R.id.seatNum);
        seatNum.setText(rail_info[4]);

        layoutlist = findViewById(R.id.seatCard);
        new GetSeat().execute(rail_);

        Button btn_update = findViewById(R.id.sure);
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ticketUpdate().execute();
                Intent intent = new Intent(checkbook.this, ending.class);
                startActivity(intent);
            }
        });

    }


    private void addView(Ticket ticket) {

        View carAppend = getLayoutInflater().inflate(R.layout.check_card, null, false);
        TextView startTime = carAppend.findViewById(R.id.startTime);
        TextView endTime = carAppend.findViewById(R.id.endTime);
        TextView carNum = carAppend.findViewById(R.id.carNum);

        TextView go = carAppend.findViewById(R.id.go);
        TextView needTime = carAppend.findViewById(R.id.needTime);
        Button appCompatButton = carAppend.findViewById(R.id.appCompatButton);

        View divider = carAppend.findViewById(R.id.divider);

        TextView seatId = carAppend.findViewById(R.id.seatId);
        TextView discountText = carAppend.findViewById(R.id.discountText);
        TextView endStation = carAppend.findViewById(R.id.endStation);
        TextView startStation = carAppend.findViewById(R.id.startStation);
        TextView date = carAppend.findViewById(R.id.date);

        String[] s = ticket.getStart().toString().split(":");
        String[] e = ticket.getEnd().toString().split(":");
        int need_time_hour = Integer.parseInt(e[0]) - Integer.parseInt(s[0]);
        int need_time_min = Integer.parseInt(e[1]) - Integer.parseInt(s[1]);
        if (need_time_min < 0){
            need_time_hour -= 1;
            need_time_min += 60;
        }
        String need_time = need_time_hour + "小時" + need_time_min +"分";

        startTime.setText(ticket.getStart().toString());
        endTime.setText(ticket.getEnd().toString());
        needTime.setText(need_time);
        carNum.setText(String.valueOf(ticket.getTag()));
        String tmp[] = ticket.getSeat().split(" ");
        seatId.setText(tmp[0]+"車 "+tmp[1]);
        discountText.setText(ticket.getDiscount());
        endStation.setText(ticket.getTo());
        startStation.setText(ticket.getFrom());
        date.setText(ticket.getDate().toString());


        layoutlist.addView(carAppend);
    }



    private class GetSeat extends AsyncTask<Rail , Void , Ticket[]> {

        private ProgressDialog progressBar;
        //進度條元件


        @Override
        protected void onPreExecute() {
            //執行前 設定可以在這邊設定
            super.onPreExecute();

            progressBar = new ProgressDialog(checkbook.this);
            progressBar.setMessage("Loading seats...");
            progressBar.setCancelable(false);
            progressBar.show();
            //初始化進度條並設定樣式及顯示的資訊。
        }

        @Override
        protected Ticket[] doInBackground(Rail... rail) {
            //執行中 在背景做事情
            Rail rail_ = rail[0];
            String[] str;
            str = rail_.NoSeat();
            tickets = rail_.BookSeat(str);

            return tickets;
        }

        @Override
        protected void onPostExecute(Ticket[] tickets) {
            //執行後 完成背景任務
            super.onPostExecute(tickets);
            int Price_total = 0;

            for(int i = 0; i < tickets.length; i++){
                addView(tickets[i]);
                Price_total += tickets[i].getPrice();
            }
            TextView totalPrice = findViewById(R.id.totalPrice);
            totalPrice.setText(String.format("%d",Price_total));

            progressBar.dismiss();
        }
    }

    private class ticketUpdate extends AsyncTask<Ticket, Void , Void> {

        private ProgressDialog progressBar;
        //進度條元件

        @Override
        protected void onPreExecute() {
            //執行前 設定可以在這邊設定
            super.onPreExecute();

            progressBar = new ProgressDialog(checkbook.this);
            progressBar.setMessage("處理訂票程序...");
            progressBar.setCancelable(false);
            progressBar.show();
            //初始化進度條並設定樣式及顯示的資訊。
        }

        @Override
        protected Void doInBackground(Ticket... tick) {
            //執行中 把每張票更新
            for (int i = 0; i < tickets.length; i++){

                tickets[i].update();
                tickets[i].updatePrice(tickets[i].getSeat(),tickets[i].getPrice());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void a) {
            super.onPostExecute(null);

            progressBar.dismiss();

        }
    }
}