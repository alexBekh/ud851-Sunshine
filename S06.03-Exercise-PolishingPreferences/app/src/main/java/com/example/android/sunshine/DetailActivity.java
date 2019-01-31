package com.example.android.sunshine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.ShareCompat;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity
        implements
        SharedPreferences.OnSharedPreferenceChangeListener
        , LoaderManager.LoaderCallbacks<String[]>
{
    
    private static final String FORECAST_SHARE_HASHTAG = " #SunshineApp";
    private int mPos = 0;
    private String mForecast;
    private TextView mWeatherDisplay;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        
        mWeatherDisplay = (TextView) findViewById(R.id.tv_display_weather);
        
        Intent intentThatStartedThisActivity = getIntent();
        
        if (intentThatStartedThisActivity != null)
        {
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_TEXT))
            {
                mForecast = intentThatStartedThisActivity.getStringExtra(Intent.EXTRA_TEXT);
                mWeatherDisplay.setText(mForecast);
            }
            if (intentThatStartedThisActivity.hasExtra(Intent.EXTRA_INDEX))
            {
                mPos = intentThatStartedThisActivity.getIntExtra(Intent.EXTRA_INDEX, 0);
            }
        }
        
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);
    }
    
    @Override
    protected void onDestroy()
    {
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        
        super.onDestroy();
    }
    
    /**
     * Uses the ShareCompat Intent builder to create our Forecast intent for sharing. We set the
     * type of content that we are sharing (just regular text), the text itself, and we return the
     * newly created Intent.
     *
     * @return The Intent to use to start our share.
     */
    private Intent createShareForecastIntent()
    {
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText(mForecast + FORECAST_SHARE_HASHTAG)
                .getIntent();
        return shareIntent;
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareForecastIntent());
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        
        if (id == R.id.action_settings)
        {
            Intent startSettingsActivity = new Intent(this, SettingsActivity.class);
            startActivity(startSettingsActivity);
            return true;
        }
        
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s)
    {
        getSupportLoaderManager()
                .restartLoader(ForecastAsyncTaskLoader.FORECAST_LOADER_ID, null, this);
    }
    
    @NonNull
    @Override
    public Loader<String[]> onCreateLoader(int i, @Nullable Bundle bundle)
    {
        return new ForecastAsyncTaskLoader(this, null);
    }
    
    @Override
    public void onLoadFinished(@NonNull Loader<String[]> loader, String[] strings)
    {
        if (strings == null || strings.length < mPos)
        {
            mForecast = "";
        }
        else
        {
            mForecast = strings[mPos];
        }
        mWeatherDisplay.setText(mForecast);
    }
    
    @Override
    public void onLoaderReset(@NonNull Loader<String[]> loader)
    {
    }
}