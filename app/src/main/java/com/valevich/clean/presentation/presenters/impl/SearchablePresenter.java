package com.valevich.clean.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.valevich.clean.domain.interactors.IStoriesSearchingInteractor;
import com.valevich.clean.domain.interactors.impl.StoriesSearchingInteractor;
import com.valevich.clean.presentation.ui.fragments.SearchableFragment;

import icepick.State;


public class SearchablePresenter extends StoriesPresenter<SearchableFragment> {

    private static final int FIND_STORIES_TASK_ID = 8;

    private IStoriesSearchingInteractor searchingInteractor;

    @State
    String query;

    @State
    int count;

    @State
    int offset;

    public SearchablePresenter(Context context) {
        super(context);
        searchingInteractor = new StoriesSearchingInteractor(getStoriesManager());
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        restartableLatestCache(
                FIND_STORIES_TASK_ID,
                () -> searchingInteractor.loadStories(query,count,offset),
                SearchableFragment::onStories,
                SearchableFragment::onError
        );
    }

    public void findStories(String query,int count,int offset) {
        this.query = query;
        this.count = count;
        this.offset = offset;
        start(FIND_STORIES_TASK_ID);
    }
}
