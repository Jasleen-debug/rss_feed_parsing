package com.example.a7rssfeed;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ArticleListAdapter extends ArrayAdapter<Article> {

    private Context context;
    private int articleLayoutID;
    public ArticleListAdapter(@NonNull Context context, int articleLayoutID, @NonNull List<Article> articles) {
        super(context, articleLayoutID, articles);

        this.context = context;
        this.articleLayoutID = articleLayoutID;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(articleLayoutID, parent, false);
        }

        TextView tvArticleTitle = convertView.findViewById(R.id.tvArticleTitle);
        TextView tvArticleAuthor = convertView.findViewById(R.id.tvArticleAuthor);
        TextView tvArticleDate = convertView.findViewById(R.id.tvArticleDate);

        Article article = getItem(position);

        tvArticleTitle.setText(article.getTitle());
        tvArticleAuthor.setText(article.getAuthor());
        tvArticleDate.setText(article.getDate());
        return convertView;
    }
}
