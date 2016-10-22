package com.dsatija.nytimessearcharticle;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dsatija.nytimessearcharticle.adapter.ArticleAdapter;
import com.dsatija.nytimessearcharticle.listeners.EndlessRecyclerViewScrollListener;
import com.dsatija.nytimessearcharticle.model.Article;
import com.dsatija.nytimessearcharticle.model.ArticleResponseVO;
import com.dsatija.nytimessearcharticle.network.RestInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    List<Article> gaggeredList =new ArrayList<>();
    ArticleAdapter adapter;
    String apiKey="dc155b1f2ab04b6c93d53d13b3a35e29";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setViews();

    }


    private void setEndlessScrolling(){
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener(gaggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                customLoadMoreDataFromApi(page);
            }
        });
    }

    // Append more data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void customLoadMoreDataFromApi(int page) {
        // Send an API request to retrieve appropriate data using the offset value as a parameter.
        //  --> Deserialize API response and then construct new objects to append to the adapter
        //  --> Notify the adapter of the changes
    }
    private void setViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.rvArticles);

        recyclerView.setHasFixedSize(true);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);

        // Add the scroll listener
        //setEndlessScrolling();
        //getArticles();
        adapter= new ArticleAdapter(this, gaggeredList);
        recyclerView.setAdapter(adapter);



        //adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
            MenuItem searchItem = menu.findItem(R.id.action_search);
            final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // Expand the search view and request focus

        searchItem.expandActionView();

        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                // perform query here
                gaggeredList=getArticles(query);
                Log.d("GAGERRED LIST",gaggeredList.toString());



                // workaround to avoid issues with some emulators and keyboard devices firing twice if a keyboard enter is used

                // see https://code.google.com/p/android/issues/detail?id=24599
                searchView.clearFocus();

                return true;
            }
            @Override

            public boolean onQueryTextChange(String newText) {

                return false;

            }

        });

        return super.onCreateOptionsMenu(menu);

    }



    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public List<Article> getArticles(String query){
        String url = "https://api.nytimes.com/";
        Log.d("Debug","url" + url);
        List<Article>articles=new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestInterface service = retrofit.create(RestInterface.class);
        Call<ArticleResponseVO> call = service.getArticles(apiKey,query);
        call.enqueue(new Callback<ArticleResponseVO>() {
            @Override
            public void onResponse(Call<ArticleResponseVO> call, Response<ArticleResponseVO> response) {
                try {

                    if(response.body()!=null&&response.body().getResponse()!=null&&response.body().getResponse()
                            .getDocs()!=null&&!response.body().getResponse().getDocs().isEmpty())
                    {
                        gaggeredList.addAll(response.body().getResponse().getDocs());
                        adapter.setmArticles(gaggeredList);
                        adapter.notifyDataSetChanged();
                        //setViews();

                        Log.d("aRITCLES GURU",gaggeredList.toString());
                    }



                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ArticleResponseVO> call, Throwable t) {
                Log.d("Debugmessage",t.getMessage());
                Log.d("Debug","failed");
                t.printStackTrace();

            }
        });
        return articles;
    }

    }



