package com.dsatija.nytimessearcharticle.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Disha on 10/20/2016.
 */
public class ArticleResponse implements Serializable {
    private Meta meta;
    private List<Article> docs = new ArrayList<Article>();

    /**
     * @return The meta
     */
    public Meta getMeta() {
        return meta;
    }

    /**
     * @param meta The meta
     */
    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    /**
     * @return The users
     */
    public List<Article> getDocs() {
        return docs;
    }

    /**
     * @param articles The articles
     */
    public void setDocs(List<Article> articles) {
        this.docs = articles;
    }


}
