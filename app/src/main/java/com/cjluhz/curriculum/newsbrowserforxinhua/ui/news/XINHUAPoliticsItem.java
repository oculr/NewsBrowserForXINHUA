package com.cjluhz.curriculum.newsbrowserforxinhua.ui.news;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class XINHUAPoliticsItem implements MultiItemEntity {
    private String href;
    private String imgsrc;
    private String title;
    private String date;
    private int itemType;
    public static final int NOIMG = 1;
    public static final int IMG = 2;

    public XINHUAPoliticsItem(String href, String imgsrc, String title, String date, int itemType){
        this.href = href;
        this.imgsrc = imgsrc;
        this.title = title;
        this.date = date;
        this.itemType = itemType;
    }

    public String getHref() {
        return href;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getImgsrc() {
        return imgsrc;
    }

    @Override
    public String toString() {
        return "XINHUAPoliticsItem{" +
                "href='" + href + '\'' +
                ", imgsrc='" + imgsrc + '\'' +
                ", title='" + title + '\'' +
                ", date='" + date + '\'' +
                '}';
    }

    @Override
    public int getItemType() {
        return itemType;
    }
}
