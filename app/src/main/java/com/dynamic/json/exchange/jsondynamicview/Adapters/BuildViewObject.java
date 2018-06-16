package com.dynamic.json.exchange.jsondynamicview.Adapters;

/**
 * Created by Vijeet on 08-06-2018.
 */

public class BuildViewObject {

    private String title;
    private int titleType;

    public BuildViewObject(String title, int titleType) {
        this.title = title;
        this.titleType = titleType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTitleType() {
        return titleType;
    }

    public void setTitleType(int titleType) {
        this.titleType = titleType;
    }
}
