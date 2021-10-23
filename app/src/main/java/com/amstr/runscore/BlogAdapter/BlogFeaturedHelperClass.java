package com.amstr.runscore.BlogAdapter;

public class BlogFeaturedHelperClass {
    int imgContent;
    String author,datePublish,title,content;

    public BlogFeaturedHelperClass(int imgContent, String author, String datePublish, String title, String content) {
        this.imgContent = imgContent;
        this.author = author;
        this.datePublish = datePublish;
        this.title = title;
        this.content = content;
    }

    public int getImgContent() {
        return imgContent;
    }

    public void setImgContent(int imgContent) {
        this.imgContent = imgContent;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDatePublish() {
        return datePublish;
    }

    public void setDatePublish(String datePublish) {
        this.datePublish = datePublish;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
