package com.cjluhz.curriculum.newsbrowserforxinhua.ui.Browser;

import com.chad.library.adapter.base.entity.MultiItemEntity;

public class PageContent implements MultiItemEntity {
    public static final int title = 0;
    public static final int dateandsource = 1;
    public static final int strong = 2;
    public static final int navyfont = 3;
    public static final int p = 4;
    public static final int img = 5;

    private String textOrImgsrc;

    private int itemType;

    public PageContent(int itemType, String textOrImgsrc){
        this.itemType = itemType;
        this.textOrImgsrc = textOrImgsrc;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public String getTextOrImgsrc() {
        return textOrImgsrc;
    }

    public void setTextOrImgsrc(String textOrImgsrc) {
        this.textOrImgsrc = textOrImgsrc;
    }
}
