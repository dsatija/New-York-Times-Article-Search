package com.dsatija.nytimessearcharticle.network;

import com.dsatija.nytimessearcharticle.model.ArticleResponseVO;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Disha on 10/20/2016.
 */
public interface RestInterface {



    @GET("svc/search/v2/articlesearch.json")
    Call<ArticleResponseVO> getArticles(@Query("api-key")String apiKey,@Query("q") String query,@Query("page") int page);

    @GET("svc/search/v2/articlesearch.json")
    Call<ArticleResponseVO> getArticles(@QueryMap Map<String,String> options);


}
