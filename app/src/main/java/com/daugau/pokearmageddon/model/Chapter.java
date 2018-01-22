package com.daugau.pokearmageddon.model;

/**
 * Created by Khiem on 3/24/2017.
 */

public class Chapter {

    public Chapter() {

    }

    public Chapter(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    private int id = 0;
    private String title = "";
    private String content = "";
}
