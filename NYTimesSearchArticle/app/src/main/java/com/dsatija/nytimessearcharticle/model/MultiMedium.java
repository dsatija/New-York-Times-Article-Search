package com.dsatija.nytimessearcharticle.model;

import java.io.Serializable;

/**
 * Created by Disha on 10/20/2016.
 */
public class MultiMedium implements Serializable {
    private Integer width;
    private String url;
    private Integer height;
    private String subtype;
    private Legacy legacy;
    private String type;

    /**
     * @return The width
     */
    public Integer getWidth() {
        return width;
    }

    /**
     * @param width The width
     */
    public void setWidth(Integer width) {
        this.width = width;
    }

    /**
     * @return The url
     */
    public String getUrl() {
        return "https://static01.nyt.com/" + url;
    }

    /**
     * @param url The url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * @return The height
     */
    public Integer getHeight() {
        return height;
    }

    /**
     * @param height The height
     */
    public void setHeight(Integer height) {
        this.height = height;
    }

    /**
     * @return The subtype
     */
    public String getSubtype() {
        return subtype;
    }

    /**
     * @param subtype The subtype
     */
    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    /**
     * @return The legacy
     */
    public Legacy getLegacy() {
        return legacy;
    }

    /**
     * @param legacy The legacy
     */
    public void setLegacy(Legacy legacy) {
        this.legacy = legacy;
    }

    /**
     * @return The type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type
     */
    public void setType(String type) {
        this.type = type;
    }


}
