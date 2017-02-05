package com.valevich.clean.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.valevich.clean.domain.interactors.IBookMarksLoadingInteractor;
import com.valevich.clean.domain.interactors.impl.BookMarksLoadingInteractor;
import com.valevich.clean.presentation.ui.fragments.BookMarksFragment;
import com.valevich.clean.presentation.ui.fragments.StoriesFragment;


public class BookMarksPresenter extends StoriesPresenter<BookMarksFragment> {

    private static final int LOAD_STORIES_TASK_ID = 2;

    private IBookMarksLoadingInteractor bookMarksInteractor;

    public BookMarksPresenter(Context context,Context activityContext) {
        super(context,activityContext);
        bookMarksInteractor = new BookMarksLoadingInteractor(getStoriesManager());
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        restartableLatestCache(
                LOAD_STORIES_TASK_ID,
                () -> bookMarksInteractor.getBookMarks(),
                StoriesFragment::onStories,
                StoriesFragment::onError);

    }

    public void loadStories() {
        start(LOAD_STORIES_TASK_ID);
    }
}
