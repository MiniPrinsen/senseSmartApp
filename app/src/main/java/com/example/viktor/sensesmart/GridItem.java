package com.example.viktor.sensesmart;

/**
 * @author Filippo Ash
 * @version 1.0.0
 * @date 11/7/2015
 */
public class GridItem extends Item {

    private int mPosition;
    private String mSubTitle;
    private int image;

    public GridItem(String title, int position, int image, int height, int width) {
        super(title, height, width);
        mPosition = position;
        this.image = image;
    }

    public int getImage(){
        return image;
    }


    public String getSubTitle() {
        return mSubTitle;
    }

    public void setSubTitle(String subTitle) {
        mSubTitle = subTitle;
    }

    public int getPosition() {
        return mPosition;
    }

    @Override
    public int getItemType() {
        return GRID_ITEM_TYPE;
    }
}