package com.valevich.clean.presentation.presenters.impl;

import android.content.Context;

import com.valevich.clean.domain.repository.specification.impl.BookMarksSqlDSpecification;
import com.valevich.clean.presentation.ui.fragments.BookMarksFragment;


public class BookMarksPresenter extends StoriesPresenter<BookMarksFragment> {

    public BookMarksPresenter(Context context) {
        super(context);
    }

    public void getBookmarkedStories() {
        loadStories(BookMarksSqlDSpecification.create());
    }
}
