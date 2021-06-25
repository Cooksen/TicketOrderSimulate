package com.example.TickerOrder.background;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.TickerOrder.MainActivity;
import com.example.TickerOrder.R;

public class ending extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ending);

        Button goback = findViewById(R.id.button2);
        goback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ending.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}