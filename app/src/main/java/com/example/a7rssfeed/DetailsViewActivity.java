package com.example.a7rssfeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import java.util.Objects;

public class DetailsViewActivity extends AppCompatActivity {
    TextView tvTitle;
    TextView tvDate;
    TextView tvDescription;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_view);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        tvTitle = findViewById(R.id.tvTitle);
        tvDate = findViewById(R.id.tvDate);
        tvDescription = findViewById(R.id.tvDescription);

        Intent intent = getIntent();
        String title = intent.getStringExtra(ListViewActivity.TITLE);
        String date = intent.getStringExtra(ListViewActivity.DATE);
        String descp = intent.getStringExtra(ListViewActivity.DESCP);


        tvTitle.setText(title);
        tvDate.setText(date);
        tvDescription.setText(descp);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.details_view_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

}