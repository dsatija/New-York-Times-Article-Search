package com.dsatija.nytimessearcharticle.model;

import java.io.Serializable;

/**
 * Created by Disha on 10/20/2016.
 */
public class ArticleResponseVO implements Serializable {
    private ArticleResponse response;
    private String status;
    private String copyright;

    /**
     * @return The response
     */
    public ArticleResponse getResponse() {
        return response;
    }

    /**
     * @param response The response
     */
    public void setResponse(ArticleResponse response) {
        this.response = response;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The copyright
     */
    public String getCopyright() {
        return copyright;
    }

    /**
     * @param copyright The copyright
     */
    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }


}
