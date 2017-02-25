package com.valevich.clean.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valevich.clean.R;
import com.valevich.clean.domain.model.Story;
import com.valevich.clean.presentation.presenters.impl.BookMarksPresenter;

import java.util.List;

import nucleus.factory.PresenterFactory;
import nucleus.factory.RequiresPresenter;

@RequiresPresenter(BookMarksPresenter.class)
public class BookMarksFragment extends StoriesFragment<BookMarksPresenter> {

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle == null) {
            getPresenter().getBookmarkedStories();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_stories,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showLoading();
    }

    @Override
    public void onStories(List<Story> stories) {
        super.onStories(stories);
        hideLoading();
    }

    @Override
    PresenterFactory<BookMarksPresenter> createPresenterFactory() {
        return () -> new BookMarksPresenter(getActivity().getApplicationContext());
    }
}
