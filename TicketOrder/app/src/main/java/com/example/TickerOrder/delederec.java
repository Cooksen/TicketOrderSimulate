package com.example.TickerOrder;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.TickerOrder.dbcode.Date;
import com.example.TickerOrder.dbcode.Ticket;
import com.example.TickerOrder.dbcode.Time;

public class delederec extends AppCompatActivity {
    String[] record;
    Date date;
    Time s;
    Time e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        Intent intent = getIntent();
        record = intent.getStringExtra("info").split(" ");
        date = new Date(record[4]);
        s = new Time(record[5]);
        e = new Time(record[6]);

        delrec dl = new delrec();
        dl.execute(record);

    }

    private class delrec extends AsyncTask<String , Void , String> {

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected String doInBackground(String... strings) {
            Date date = new Date(record[4]);
            Time s = new Time(record[5]);
            Time e = new Time(record[6]);
            Ticket ticket = new Ticket(Integer.parseInt(record[1]),date,s,e,Integer.parseInt(record[9])
                    ,ticketCheck.intToSta(Integer.parseInt(record[2])),ticketCheck.intToSta(Integer.parseInt(record[3])),
                    record[7]+" "+record[8],"null",record[0]);
            ticket.DeleteRec();
            return null;
        }

        @Override
        protected void onPostExecute(String result){
            Toast.makeText(delederec.this, "成功", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.setClass(delederec.this,ticketCheck.class );
            intent.putExtra("ID",record[0]);
            startActivity(intent);
        }
    }

}
