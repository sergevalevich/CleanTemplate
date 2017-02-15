package com.valevich.clean.domain.interactors.impl;

import com.valevich.clean.domain.data.base.IStoriesManager;
import com.valevich.clean.domain.interactors.IBookMarksLoadingInteractor;
import com.valevich.clean.domain.model.Story;

import java.util.List;

import rx.Observable;


public class BookMarksLoadingInteractor extends StoriesInteractor implements IBookMarksLoadingInteractor {

    public BookMarksLoadingInteractor(IStoriesManager storiesManager) {
        super(storiesManager);
    }

    @Override
    public Observable<List<Story>> getBookMarks(int count, int offset) {
        return storiesManager.getBookMarks(count, offset);
    }
}
