package com.example.android.sunshine.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.example.android.sunshine.data.WeatherContract;
import com.example.android.sunshine.utilities.NetworkUtils;
import com.example.android.sunshine.utilities.OpenWeatherJsonUtils;

import java.net.URL;

//  TODO (1) Create a class called SunshineSyncTask
class SunshineSyncTask
{
    public static synchronized void syncWeather(Context context)
    {
        ContentValues[] valuesFromJson;
        try
        {
            final URL url = NetworkUtils.getUrl(context);
            final String responseFromServer = NetworkUtils.getResponseFromHttpUrl(url);
            valuesFromJson = OpenWeatherJsonUtils.getWeatherContentValuesFromJson(context, responseFromServer);
    
            if (valuesFromJson != null && valuesFromJson.length > 0)
            {
                final ContentResolver contentResolver = context.getContentResolver();
                int itemsDeleted = contentResolver.delete(WeatherContract.WeatherEntry.CONTENT_URI, null, null);
                if (itemsDeleted == -1)
                    throw new Exception("Can't delete old data from content provider");
        
                int itemsInserted = contentResolver.bulkInsert(WeatherContract.WeatherEntry.CONTENT_URI, valuesFromJson);
                if (itemsInserted != valuesFromJson.length)
                    throw new Exception("Can't insert new data to content provider");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
//  TODO (2) Within SunshineSyncTask, create a synchronized public static void method called syncWeather
//      TODO (3) Within syncWeather, fetch new weather data
//      TODO (4) If we have valid results, delete the old data and insert the new