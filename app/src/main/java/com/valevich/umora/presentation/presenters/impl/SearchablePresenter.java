package com.valevich.umora.presentation.presenters.impl;

import com.valevich.umora.domain.repository.specification.impl.SearchSqlDSpecification;
import com.valevich.umora.presentation.ui.fragments.SearchableFragment;


public class SearchablePresenter extends StoriesPresenter<SearchableFragment> {
    public void findStories(String query) {
        loadStories(SearchSqlDSpecification.create(query));
    }
}
