package com.valevich.clean.presentation.ui.fragments;

import com.valevich.clean.presentation.presenters.impl.BookMarksPresenter;

import nucleus.factory.PresenterFactory;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(BookMarksPresenter.class)
public class BookMarksFragment extends StoriesFragment<BookMarksPresenter> {

    @Override
    void getStories() {
        getPresenter().loadStories();
    }

    @Override
    PresenterFactory<BookMarksPresenter> createPresenterFactory() {
        return () -> new BookMarksPresenter(getActivity().getApplicationContext(),getActivity());
    }
}
