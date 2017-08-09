package com.news.www.news.Model;

/**
 * Created by SS on 16-12-8.
 */
public class BannerBean {
    private String[] image;
    private String[] title;
    private String[] toUrl;
    public BannerBean() {
    }

    public String[] getImage() {
        return image;
    }

    public void setImage(String[] image) {
        this.image = image;
    }

    public String[] getTitle() {
        return title;
    }

    public void setTitle(String[] title) {
        this.title = title;
    }

    public String[] getToUrl() {
        return toUrl;
    }

    public void setToUrl(String[] toUrl) {
        this.toUrl = toUrl;
    }
}
