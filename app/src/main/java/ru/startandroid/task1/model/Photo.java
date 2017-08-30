package ru.startandroid.task1.model;

/**
 * Created by User on 30.08.2017.
 */

public class Photo {
    private String url;
    public void Photo() {
        url = "";
    }
    public void Photo(String url) {
        this.url = url;
    }
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
