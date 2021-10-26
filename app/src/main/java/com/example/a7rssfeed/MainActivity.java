package com.example.a7rssfeed;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.preference.PreferenceManager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener  { // second activity
    public static final String CODE = "com.example.a7rssfeed.CODE";

    TextView tvTitle1;
    TextView tvTitle2;
    TextView tvTitle3;

    ImageView ivImage1;
    ImageView ivImage2;
    ImageView ivImage3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        tvTitle1 = findViewById(R.id.tvTitle1);
        tvTitle1.setOnClickListener(onClickFeed1);
        ivImage1 = findViewById(R.id.ivImage1);
        ivImage1.setOnClickListener(onClickFeed1);

        tvTitle2 = findViewById(R.id.tvTitle2);
        tvTitle2.setOnClickListener(onClickFeed2);
        ivImage2 = findViewById(R.id.ivImage2);
        ivImage2.setOnClickListener(onClickFeed2);

        tvTitle3 = findViewById(R.id.tvTitle3);
        tvTitle3.setOnClickListener(onClickFeed3);
        ivImage3 = findViewById(R.id.ivImage3);
        ivImage3.setOnClickListener(onClickFeed3);

        setupSharedPreferences();

    }
    private View.OnClickListener onClickFeed1 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
            intent.putExtra(CODE,"https://www.cbc.ca/cmlink/rss-arts");
            startActivity(intent);
        }
    };
    private View.OnClickListener onClickFeed2 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
            intent.putExtra(CODE,"https://www.cbc.ca/cmlink/rss-sports-figureskating");
            startActivity(intent);
        }
    };
    private View.OnClickListener onClickFeed3 = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, ListViewActivity.class);
            intent.putExtra(CODE,"https://www.cbc.ca/cmlink/rss-Indigenous");
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }   else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener((SharedPreferences.OnSharedPreferenceChangeListener) this);
    }

    // Method to set Visibility of Text.
    private void setTextVisible(boolean display_text) {
        if (display_text) {
            tvTitle1.setVisibility(View.VISIBLE);
            tvTitle2.setVisibility(View.VISIBLE);
            tvTitle3.setVisibility(View.VISIBLE);
        } else {
            tvTitle1.setVisibility(View.INVISIBLE);
            tvTitle2.setVisibility(View.INVISIBLE);
            tvTitle3.setVisibility(View.INVISIBLE);
        }
    }

    // Method to set Visibility of Image.
    private void setImageVisible(boolean display_image) {
        if (display_image) {
            ivImage1.setVisibility(View.VISIBLE);
            ivImage2.setVisibility(View.VISIBLE);
            ivImage3.setVisibility(View.VISIBLE);
        } else {
            ivImage1.setVisibility(View.INVISIBLE);
            ivImage2.setVisibility(View.INVISIBLE);
            ivImage3.setVisibility(View.INVISIBLE);
        }
    }

    // Method to set Color of Text.
    private void changeTextColor(String pref_color_value) {
        Log.d("JK", pref_color_value);
        if (pref_color_value.equals("red")) {
            tvTitle1.setTextColor(Color.RED);
            tvTitle2.setTextColor(Color.RED);
            tvTitle3.setTextColor(Color.RED);
        } else if(pref_color_value.equals("green")) {
            tvTitle1.setTextColor(Color.GREEN);
            tvTitle2.setTextColor(Color.GREEN);
            tvTitle3.setTextColor(Color.GREEN);
        } else {
            tvTitle1.setTextColor(Color.BLUE);
            tvTitle2.setTextColor(Color.BLUE);
            tvTitle3.setTextColor(Color.BLUE);
        }
    }


    // Method to set Size of Text.
    private void changeTextSize(Float i) {
        tvTitle1.setTextSize(i);
        tvTitle2.setTextSize(i);
        tvTitle3.setTextSize(i);
    }

    // Method to pass value from SharedPreferences
    private void loadColorFromPreference(SharedPreferences sharedPreferences) {
        Log.d("JK",sharedPreferences.getString(getString(R.string.pref_color_key),getString(R.string.pref_color_red_value)));
        changeTextColor(sharedPreferences.getString(getString(R.string.pref_color_key),getString(R.string.pref_color_red_value)));
    }

    private void loadSizeFromPreference(SharedPreferences sharedPreferences) {
        float minSize = Float.parseFloat(sharedPreferences.getString(getString(R.string.pref_size_key), "16.0"));
        changeTextSize(minSize);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        switch (key) {
            case "display_text":
                setTextVisible(sharedPreferences.getBoolean("display_text", true));
                break;
            case "display_image":
                setImageVisible(sharedPreferences.getBoolean("display_image", true));
                break;
            case "color":
                loadColorFromPreference(sharedPreferences);
                break;
            case "size":
                loadSizeFromPreference(sharedPreferences);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("JK","Shared preferences destroy");
    }
}
