package com.valevich.clean.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Story implements Parcelable {

    public static final int DEFAULT_COUNT = 10;
    public static final int DEFAULT_OFFSET = 0;

    private String text;
    private String textLow;
    private String site;
    private String categoryName;
    private boolean isBookMarked;
    private long date;

    public Story(String text, String textLow, String site, String categoryName, Boolean isBookMarked,long date) {
        this.text = text;
        this.textLow = textLow;
        this.site = site;
        this.categoryName = categoryName;
        this.isBookMarked = isBookMarked;
        this.date = date;
    }

    protected Story(Parcel in) {
        text = in.readString();
        textLow = in.readString();
        site = in.readString();
        categoryName = in.readString();
        isBookMarked = in.readByte() != 0;
        date = in.readLong();
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public boolean isBookMarked() {
        return isBookMarked;
    }

    public void setBookMarked(boolean bookMarked) {
        isBookMarked = bookMarked;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTextLow() {
        return textLow;
    }

    public void setTextLow(String textLow) {
        this.textLow = textLow;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeString(textLow);
        parcel.writeString(site);
        parcel.writeString(categoryName);
        parcel.writeByte((byte) (isBookMarked ? 1 : 0));
        parcel.writeLong(date);
    }
}
