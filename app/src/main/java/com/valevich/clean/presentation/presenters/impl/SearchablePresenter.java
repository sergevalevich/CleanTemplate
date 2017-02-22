package com.valevich.clean.presentation.presenters.impl;

import android.content.Context;
import android.os.Bundle;

import com.valevich.clean.presentation.ui.fragments.SearchableFragment;

import icepick.State;


public class SearchablePresenter extends StoriesPresenter<SearchableFragment> {

    private static final int FIND_STORIES_TASK_ID = 8;

    @State
    String query;

    @State
    int count;

    @State
    int offset;

    public SearchablePresenter(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedState) {
        super.onCreate(savedState);
        restartableLatestCache(
                FIND_STORIES_TASK_ID,
                () -> getRepository().find(query,count,offset),
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
