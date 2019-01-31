package com.example.android.sunshine;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.example.android.sunshine.data.SunshinePreferences;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

class ForecastAsyncTaskLoader extends AsyncTaskLoader<String[]>
{
    static final int FORECAST_LOADER_ID = 0;
    
    /* This String array will hold and help cache our weather data */
    private String[] mWeatherData = null;
    private ProgressBar mProgressBar;
    
    ForecastAsyncTaskLoader(Context context, ProgressBar progressBar)
    {
        super(context);
        mProgressBar = progressBar;
    }
    
    /**
     * Subclasses of AsyncTaskLoader must implement this to take care of loading their data.
     */
    @Override
    protected void onStartLoading()
    {
        if (mWeatherData != null)
        {
            deliverResult(mWeatherData);
        }
        else
        {
            if(mProgressBar != null)
                mProgressBar.setVisibility(View.VISIBLE);
            forceLoad();
        }
    }
    
    /**
     * This is the method of the AsyncTaskLoader that will load and parse the JSON data
     * from OpenWeatherMap in the background.
     *
     * @return Weather data from OpenWeatherMap as an array of Strings.
     *         null if an error occurs
     */
    @Override
    public String[] loadInBackground()
    {
        
        String locationQuery = SunshinePreferences
                .getPreferredWeatherLocation(getContext());

        String unitsFormatQuery = SunshinePreferences
                .getPreferredUnitsFormat(getContext());
                
        URL weatherRequestUrl = NetworkUtils.buildUrl(locationQuery, unitsFormatQuery);
        Log.d(getClass().getSimpleName(), "loadInBackground: URL: " + weatherRequestUrl.toString());
        
        try
        {
            String jsonWeatherResponse = NetworkUtils
                    .getResponseFromHttpUrl(weatherRequestUrl);
    
            return OpenWeatherJsonUtils
                    .getSimpleWeatherStringsFromJson(getContext(), jsonWeatherResponse);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * Sends the result of the load to the registered listener.
     *
     * @param data The result of the load
     */
    public void deliverResult(String[] data)
    {
        mWeatherData = data;
        super.deliverResult(data);
    }
}
