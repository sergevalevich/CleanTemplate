package com.valevich.clean.presentation.ui.fragments;

import com.valevich.clean.domain.model.Story;
import com.valevich.clean.presentation.presenters.impl.BookMarksPresenter;

import nucleus.factory.PresenterFactory;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(BookMarksPresenter.class)
public class BookMarksFragment extends StoriesFragment<BookMarksPresenter> {

    @Override
    void getStories() {
        getPresenter().loadStories(Story.DEFAULT_COUNT,Story.DEFAULT_OFFSET);
    }

    @Override
    PresenterFactory<BookMarksPresenter> createPresenterFactory() {
        return () -> new BookMarksPresenter(getActivity().getApplicationContext());
    }
}
