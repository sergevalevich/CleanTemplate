package com.valevich.umora.presentation.presenters.impl;

import android.content.Context;

import com.valevich.umora.domain.repository.specification.impl.BookMarksSqlDSpecification;
import com.valevich.umora.presentation.ui.fragments.BookMarksFragment;


public class BookMarksPresenter extends StoriesPresenter<BookMarksFragment> {

    public BookMarksPresenter(Context context) {
        super(context);
    }

    public void getBookmarkedStories() {
        loadStories(BookMarksSqlDSpecification.create());
    }
}
