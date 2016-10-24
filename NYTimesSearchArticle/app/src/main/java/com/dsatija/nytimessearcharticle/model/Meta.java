package com.dsatija.nytimessearcharticle.model;

import java.io.Serializable;

/**
 * Created by Disha on 10/20/2016.
 */
public class Meta implements Serializable {
    private Integer hits;
    private Integer time;
    private Integer offset;

    /**
     * @return The hits
     */
    public Integer getHits() {
        return hits;
    }

    /**
     * @param hits The hits
     */
    public void setHits(Integer hits) {
        this.hits = hits;
    }

    /**
     * @return The time
     */
    public Integer getTime() {
        return time;
    }

    /**
     * @param time The time
     */
    public void setTime(Integer time) {
        this.time = time;
    }

    /**
     * @return The offset
     */
    public Integer getOffset() {
        return offset;
    }

    /**
     * @param offset The offset
     */
    public void setOffset(Integer offset) {
        this.offset = offset;
    }


}
