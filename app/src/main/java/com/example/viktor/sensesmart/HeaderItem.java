package com.example.viktor.sensesmart;


/**
 * Created by Gustaf on 16-08-04.
 */

/**
 * @author Filippo Ash
 * @version 1.0.0
 * @date 11/7/2015
 */
public class HeaderItem extends Item {
    int height;
    int image;
    public HeaderItem(String title, int bmp, int height, int width) {
        super(title, height, width); image = bmp;
    }

    public int getImage() {
        return image;
    }

    @Override
    public int getItemType() {
        return HEADER_ITEM_TYPE;
    }
}
