package com.example.android.sunshine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>
{
    private String[] mWeatherData;
    
    public ForecastAdapter()
    {
    }
    
    public ForecastAdapter(String[] weatherData)
    {
        mWeatherData = weatherData;
    }
    
    @NonNull
    @Override
    public ForecastViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.forecast_list_item, viewGroup, false);
        ForecastViewHolder viewHolder = new ForecastViewHolder(view);
        return viewHolder;
    }
    
    @Override
    public void onBindViewHolder(@NonNull ForecastViewHolder forecastViewHolder, int i)
    {
        if (mWeatherData != null)
        {
            forecastViewHolder.mWeatherDataTextView.setText(mWeatherData[i]);
    
        }
    }
    
    @Override
    public int getItemCount()
    {
        return mWeatherData != null ? mWeatherData.length : 0;
    }
    
    public class ForecastViewHolder extends RecyclerView.ViewHolder
    {
        TextView mWeatherDataTextView;
        
        public ForecastViewHolder(@NonNull View itemView)
        {
            super(itemView);
            mWeatherDataTextView = itemView.findViewById(R.id.tv_weather_data);
        }
    }
    
    void setWeatherData(String[] data)
    {
        if (data != null)
        {
            mWeatherData = data;
            notifyDataSetChanged();
        }
    }
}
