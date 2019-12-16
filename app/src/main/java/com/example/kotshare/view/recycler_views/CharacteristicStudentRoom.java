package com.example.kotshare.view.recycler_views;

public class CharacteristicStudentRoom
{
    private int mImageResource;
    private String mText;

    public CharacteristicStudentRoom(int imageResource, String text)
    {
        this.mImageResource = imageResource;
        this.mText = text;
    }

    public int getImageResource()
    {
        return mImageResource;
    }
    public String getText()
    {
        return mText;
    }
}
