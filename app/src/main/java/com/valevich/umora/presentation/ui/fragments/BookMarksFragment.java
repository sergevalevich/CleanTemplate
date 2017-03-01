package com.valevich.umora.presentation.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valevich.umora.R;
import com.valevich.umora.presentation.presenters.impl.BookMarksPresenter;

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
        return inflater.inflate(R.layout.fragment_list_progress,container,false);
    }

}
