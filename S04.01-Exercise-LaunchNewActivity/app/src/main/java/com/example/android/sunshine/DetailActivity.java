package com.example.android.sunshine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity
{
    private TextView mDetailForecastTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
    
        mDetailForecastTextView = findViewById(R.id.tv_detail_forecast);
    
        Intent intent = getIntent();
        if (intent.hasExtra(Intent.EXTRA_TEXT))
        {
            mDetailForecastTextView.setText(intent.getStringExtra(Intent.EXTRA_TEXT));
        }
    }
}
