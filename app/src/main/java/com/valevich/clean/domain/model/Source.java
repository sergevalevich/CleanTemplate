package com.valevich.clean.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

public class Source implements Parcelable {
    private String site;
    private List<Category> categories;

    public Source(String site, List<Category> categories) {
        this.site = site;
        this.categories = categories;
    }

    protected Source(Parcel in) {
        site = in.readString();
        categories = in.createTypedArrayList(Category.CREATOR);
    }

    public static final Creator<Source> CREATOR = new Creator<Source>() {
        @Override
        public Source createFromParcel(Parcel in) {
            return new Source(in);
        }

        @Override
        public Source[] newArray(int size) {
            return new Source[size];
        }
    };

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(site);
        parcel.writeTypedList(categories);
    }
}
