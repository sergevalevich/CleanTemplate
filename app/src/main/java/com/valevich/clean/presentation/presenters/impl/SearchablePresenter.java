package com.valevich.clean.presentation.presenters.impl;

import android.content.Context;

import com.valevich.clean.domain.repository.specification.impl.SearchSqlDSpecification;
import com.valevich.clean.presentation.ui.fragments.SearchableFragment;


public class SearchablePresenter extends StoriesPresenter<SearchableFragment> {

    public SearchablePresenter(Context context) {
        super(context);
    }

    public void findStories(String query) {
        loadStories(SearchSqlDSpecification.create(query));
    }
}
