package com.dsatija.nytimessearcharticle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dsatija.nytimessearcharticle.R;
import com.dsatija.nytimessearcharticle.model.Article;

import java.util.List;

public class ArticleAdapter extends
        RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    public List<Article> getmArticles() {
        return mArticles;
    }

    public void setmArticles(List<Article> mArticles) {
        this.mArticles = mArticles;
    }

    private List<Article> mArticles;
    // Store the context for easy access
    private Context mContext;

    public ArticleAdapter(Context context, List<Article> articles) {
        mArticles = articles;
        mContext = context;
    }

    private Context getContext() {
        return mContext;
    }

    @Override
    public ArticleAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.item_article_result, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(ArticleAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Article article = mArticles.get(position);
        // Set item views based on your views and data model
        TextView textView = viewHolder.tvTitle;

        if(article.getHeadLine()!=null) {
            textView.setText(article.getHeadLine().getMain());
        }
        ImageView ivImage = viewHolder.ivImage;
        if(article.getMultimedia()!=null && !article.getMultimedia().isEmpty()) {
            String thumbNail = article.getMultimedia().get(0).getUrl();
            if (!TextUtils.isEmpty(thumbNail)) {
                //Picasso.with(getContext()).load(thumbnail).into(imageView);
                Glide.with(getContext()).load(thumbNail).fitCenter().into(ivImage);
            }
        }
        super.onViewRecycled(viewHolder);
       // Glide.clear(ivImage);


    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return mArticles.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvTitle;
        public ImageView ivImage;

        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
        }


    }

    public void swap(List<Article> articles){
        mArticles.clear();
        mArticles.addAll(articles);
        notifyDataSetChanged();
    }
}