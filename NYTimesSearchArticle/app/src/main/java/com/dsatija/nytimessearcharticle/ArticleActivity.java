package com.dsatija.nytimessearcharticle;

import android.net.Uri;
import android.os.Bundle;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.dsatija.nytimessearcharticle.model.Article;

public class ArticleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        String url = null;
        Article article = (Article) this.getIntent().getSerializableExtra("article");
        if (article != null && article.getWeb_url() != null) {
            url = article.getWeb_url();
            Log.d("url in child activity", url);
            CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
            CustomTabsIntent customTabsIntent = builder.build();
            customTabsIntent.launchUrl(this, Uri.parse(url));
        }
        finish();
    }
}
