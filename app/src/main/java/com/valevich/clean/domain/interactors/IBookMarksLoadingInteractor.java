package com.valevich.clean.domain.interactors;

import com.valevich.clean.domain.interactors.base.Interactor;
import com.valevich.clean.domain.model.Story;

import java.util.List;

import rx.Observable;

public interface IBookMarksLoadingInteractor extends Interactor {
    Observable<List<Story>> getBookMarks(int count, int offset);
}
