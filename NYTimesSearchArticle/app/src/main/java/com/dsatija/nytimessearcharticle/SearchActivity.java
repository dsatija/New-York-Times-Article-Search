package com.dsatija.nytimessearcharticle;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;

import com.dsatija.nytimessearcharticle.adapter.ArticleAdapter;
import com.dsatija.nytimessearcharticle.fragments.FilterFragment;
import com.dsatija.nytimessearcharticle.listeners.EndlessRecyclerViewScrollListener;
import com.dsatija.nytimessearcharticle.listeners.RecyclerItemClickListener;
import com.dsatija.nytimessearcharticle.model.Article;
import com.dsatija.nytimessearcharticle.model.ArticleResponseVO;
import com.dsatija.nytimessearcharticle.model.SearchFilter;
import com.dsatija.nytimessearcharticle.network.RestInterface;

import org.apache.commons.lang3.StringUtils;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity  implements FilterFragment.OnSettingsChangeListener, DatePickerDialog.OnDateSetListener {
    int page = 0;
    String mainPageQuery = null;
    RecyclerView recyclerView;
    private StaggeredGridLayoutManager gaggeredGridLayoutManager;
    List<Article> gaggeredList = new ArrayList<>();
    ArticleAdapter adapter;
    String apiKey = "dc155b1f2ab04b6c93d53d13b3a35e29";
    FilterFragment settingsDialog;
    SearchFilter searchFilter;
    public static final String FILENAME = "searchFilter.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchFilter = loadSearchFilter();
        if (isNetworkAvailable()) {
            setViews();
        } else {
            Snackbar.make(this.getCurrentFocus(), "No internet connection!", Snackbar.LENGTH_LONG)
                    .setAction("RETRY", null).show();
        }

    }

    private SearchFilter loadSearchFilter() {
        SearchFilter filter = new SearchFilter();
        try {
            FileInputStream fis = this.openFileInput(FILENAME);
            ObjectInputStream is = new ObjectInputStream(fis);
            filter = (SearchFilter) is.readObject();
            is.close();
            fis.close();
            return (filter);
        } catch (ClassNotFoundException cnfe) {
            Log.e("Exception", "ClassNotFoundException: " + cnfe.toString());
        } catch (IOException e) {
            Log.e("Exception", "IOException: " + e.toString());
        }
        return filter;
    }


    private void setEndlessScrolling() {
        recyclerView.addOnScrollListener(new EndlessRecyclerViewScrollListener
                (gaggeredGridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to the bottom of the list
                customLoadMoreDataFromApi(mainPageQuery, page);
            }
        });
    }

    // Append more data into the adapter
    // This method probably sends out a network request and appends new data items to your adapter.
    public void customLoadMoreDataFromApi(String query, int page) {
        // Send an API request to retrieve appropriate data using the offset value as a parameter.
        //  --> Deserialize API response and then construct new objects to append to the adapter
        //  --> Notify the adapter of the changes
        getArticles(query, page);
    }

    private void setViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView) findViewById(R.id.rvArticles);
        recyclerView.setHasFixedSize(true);
        gaggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(gaggeredGridLayoutManager);
        // Add the scroll listener
        setEndlessScrolling();
        //getArticles();
        adapter = new ArticleAdapter(this, gaggeredList);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerView,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {


                                Intent i = new Intent(getApplicationContext(), ArticleActivity.class);
                                Article article = gaggeredList.get(position);
                                i.putExtra("article", article);
                                startActivity(i);

                            }

                            @Override
                            public void onItemLongClick(View view, int position) {
                                // ...
                            }
                        }));
        getArticles("top stories",0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        MenuItem fiterItem = menu.findItem(R.id.action_filter);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // Expand the search view and request focus
        searchItem.expandActionView();
        searchView.requestFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(gaggeredList!=null&&!gaggeredList.isEmpty()){
                    gaggeredList.clear();
                }
                mainPageQuery = query;
                // perform query here
                gaggeredList = getArticles(query, page);
                Log.d("GAGERRED LIST", gaggeredList.toString());
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
        if (id == R.id.action_filter) {
            showSettingsDialog();
            return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private void showSettingsDialog() {
        FragmentManager fm = getSupportFragmentManager();
        settingsDialog = FilterFragment.newInstance(searchFilter);
        settingsDialog.show(fm, "fragment_edit_name");

    }

    public List<Article> getArticles(String query, int page) {
        String url = "https://api.nytimes.com/";
        Map<String,String>queryParams=new HashMap<>();
        Log.d("Debug", "url" + url);
        queryParams=getQueryParams(query,page);
        List<Article> articles = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RestInterface service = retrofit.create(RestInterface.class);
        Call<ArticleResponseVO> call = service.getArticles(queryParams);
        call.enqueue(new Callback<ArticleResponseVO>() {
            @Override
            public void onResponse(Call<ArticleResponseVO> call, Response<ArticleResponseVO> response) {
                try {
                    if (response.body() != null && response.body().getResponse() != null && response.body().getResponse()
                            .getDocs() != null && !response.body().getResponse().getDocs().isEmpty()) {
                        gaggeredList.addAll(response.body().getResponse().getDocs());
                        adapter.setmArticles(gaggeredList);
                        adapter.notifyDataSetChanged();
                        //setViews();
                        Log.d("Articles:", gaggeredList.toString());
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<ArticleResponseVO> call, Throwable t) {
                Log.d("Debugmessage", t.getMessage());
                Log.d("Debug", "failed");
                t.printStackTrace();

            }
        });
        return articles;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        populateSetDate(year, month + 1, dayOfMonth);
    }

    public void populateSetDate(int year, int month, int day) {
        if (settingsDialog != null) {
            settingsDialog.populateSetDate(year, month, day);
        }
    }

    private void saveSearchFilter(SearchFilter filter) {
        try {
            FileOutputStream fos = this.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream os = new ObjectOutputStream(fos);
            os.writeObject(filter);
            os.close();
            fos.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void saveSearchFilterSettings(SearchFilter newSearchFilter) {
        searchFilter = newSearchFilter;
        saveSearchFilter(searchFilter);
        if(gaggeredList!=null&&!gaggeredList.isEmpty()){
            gaggeredList.clear();
        }
        getArticles(mainPageQuery,0);
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }
    public boolean isOnline() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8");
            int     exitValue = ipProcess.waitFor();
            return (exitValue == 0);
        } catch (IOException e)          { e.printStackTrace(); }
        catch (InterruptedException e) { e.printStackTrace(); }
        return false;
    }

    public Map<String,String>getQueryParams(String query,int page){
        Map<String,String>params=new HashMap<>();
        mainPageQuery=query;
        params.put("api-key",apiKey);
        params.put("q",query);
        params.put("page",String.valueOf(page));
        String beginDateYYYYMMDD = searchFilter.getBeginDate(SearchFilter.FORMAT_YYYYMMDD);
        if (!TextUtils.isEmpty(beginDateYYYYMMDD)) {
            params.put("begin_date", beginDateYYYYMMDD);
        }
        String sortOrder = searchFilter.getSortOrder();
        if (!TextUtils.isEmpty(sortOrder)) {
            params.put("sort", sortOrder);
        }

        Set ndTopics = searchFilter.getNewsDeskTopics();
        if (ndTopics.size() > 0) {
            // &fq=news_desk:("Sports" "Foreign")
            params.put("fq", "news_desk:(\"" + StringUtils.join(ndTopics.toArray(), "\" \"") + "\")");
        }
        Log.d("DEBUG", ">>>>>" + params.toString());
        return params;
    }
}



