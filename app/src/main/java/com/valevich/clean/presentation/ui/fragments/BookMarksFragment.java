package com.valevich.clean.presentation.ui.fragments;

import com.valevich.clean.domain.model.Story;
import com.valevich.clean.presentation.presenters.impl.BookMarksPresenter;

import java.util.List;

import nucleus.factory.PresenterFactory;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(BookMarksPresenter.class)
public class BookMarksFragment extends StoriesFragment<BookMarksPresenter> {

    @Override
    public void onStories(List<Story> stories) {
        super.onStories(stories);
        hideLoading();
    }

    @Override
    void subscribeToUpdates() {
        getPresenter().loadStories(Story.DEFAULT_COUNT,Story.DEFAULT_OFFSET);
    }

    @Override
    void showLoading() {
        swipe.setEnabled(true);
        swipe.setRefreshing(true);
    }

    @Override
    void hideLoading() {
        swipe.setRefreshing(false);
        swipe.setEnabled(false);
    }

    @Override
    PresenterFactory<BookMarksPresenter> createPresenterFactory() {
        return () -> new BookMarksPresenter(getActivity().getApplicationContext());
    }
}
