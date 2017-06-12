package com.example.viktor.sensesmart;

/**
 * @author Filippo Ash
 * @version 1.0.0
 * @date 11/7/2015
 */
public abstract class Item {

    public static final int HEADER_ITEM_TYPE = 0;
    public static final int GRID_ITEM_TYPE = 1;
    private String mItemTitle;
    private int height, width;

    public Item(String title, int height, int width) {
        mItemTitle = title;
        this.height = height;
        this.width = width;
    }

    public String getItemTitle() {
        return mItemTitle;
    }

    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }

    public abstract int getItemType();
    public abstract int getImage();


}