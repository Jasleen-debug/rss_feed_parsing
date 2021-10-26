package com.example.a7rssfeed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Objects;

import javax.net.ssl.HttpsURLConnection;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ListViewActivity extends AppCompatActivity {
    public static final String TITLE = "com.example.a7rssfeed.TITLE";
    public static final String DATE = "com.example.a7rssfeed.DATE";
    public static final String DESCP = "com.example.a7rssfeed.DESCP";


    ListView mListView;
    ArticleListAdapter articleListAdapter;
    String code;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        code = intent.getStringExtra(MainActivity.CODE);
        Log.d("JK","I am here");
        ListViewActivity.DownloadAndParseRSS newInstance = new ListViewActivity.DownloadAndParseRSS();
        newInstance.execute(code);
    }
    private class DownloadAndParseRSS extends AsyncTask<String,Void, List<Article>> {

        @Override
        protected List<Article> doInBackground(String... urls) {
            String myFeedRSS = urls[0];
            URL rssURL = null;
            HttpsURLConnection connection = null;
            InputStream inputStream = null;

            try {
                rssURL = new URL(myFeedRSS);
                connection = (HttpsURLConnection) rssURL.openConnection();
                inputStream = connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            SAXParserFactory spf = SAXParserFactory.newInstance();
            RSSParseHandler rssParseHandler = null;

            try {
                SAXParser saxParser = spf.newSAXParser();

                rssParseHandler = new RSSParseHandler();
                saxParser.parse(inputStream, rssParseHandler);
            } catch (ParserConfigurationException | SAXException | IOException e) {
                e.printStackTrace();
            }

            assert rssParseHandler != null;
            return rssParseHandler.articles;
        }

        @Override
        protected void onPostExecute(List<Article> articles) {
            super.onPostExecute(articles);
            mListView = findViewById(R.id.lv_main_list_view);
            articleListAdapter = new ArticleListAdapter(ListViewActivity.this,R.layout.article_layout, articles);

            mListView.setAdapter(articleListAdapter);
            mListView.setOnItemClickListener(articleClick);
        }
    }
    AdapterView.OnItemClickListener articleClick = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
           Article article = (Article) adapterView.getItemAtPosition(position);
           // start a new activity here maybe the full article show page
            //Toast.makeText(ListViewActivity.this, article.getTitle(), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ListViewActivity.this, DetailsViewActivity.class);
            intent.putExtra(TITLE,article.getTitle());
            intent.putExtra(DATE,article.getDate());
            intent.putExtra(DESCP,article.getDescription());
            startActivity(intent);
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.list_view_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_refresh) {
            ListViewActivity.DownloadAndParseRSS newInstance2 = new ListViewActivity.DownloadAndParseRSS();
            Log.d("JK","I am refreshing");
            newInstance2.execute(code);
            Log.d("JK","I am done now");
        }   else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}