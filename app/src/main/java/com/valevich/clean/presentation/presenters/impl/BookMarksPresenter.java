package com.valevich.clean.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.valevich.clean.domain.interactors.IBookMarksLoadingInteractor;
import com.valevich.clean.domain.interactors.impl.BookMarksLoadingInteractor;
import com.valevich.clean.presentation.ui.fragments.BookMarksFragment;

import icepick.State;


public class BookMarksPresenter extends StoriesPresenter<BookMarksFragment> {

    private static final int LOAD_STORIES_TASK_ID = 2;

    private IBookMarksLoadingInteractor bookMarksInteractor;

    @State
    int count;

    @State
    int offset;

    public BookMarksPresenter(Context context) {
        super(context);
        bookMarksInteractor = new BookMarksLoadingInteractor(getStoriesManager());
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        restartableLatestCache(
                LOAD_STORIES_TASK_ID,
                () -> bookMarksInteractor.getBookMarks(count,offset),
                BookMarksFragment::onStories,
                BookMarksFragment::onError);

    }

    public void loadStories(int count, int offset) {
        this.count = count;
        this.offset = offset;
        start(LOAD_STORIES_TASK_ID);
    }
}
