package com.valevich.clean.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.auto.value.AutoValue;

public class Story implements Parcelable {

    public static final int DEFAULT_COUNT = 3;

    private String text;
    private String site;
    private String categoryName;
    private boolean isBookMarked;

    public Story(String text, String site, String categoryName, Boolean isBookMarked) {
        this.text = text;
        this.site = site;
        this.categoryName = categoryName;
        this.isBookMarked = isBookMarked;
    }

    protected Story(Parcel in) {
        text = in.readString();
        site = in.readString();
        categoryName = in.readString();
        isBookMarked = in.readByte() != 0;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(text);
        parcel.writeString(site);
        parcel.writeString(categoryName);
        parcel.writeByte((byte) (isBookMarked ? 1 : 0));
    }
}
