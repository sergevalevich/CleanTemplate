package com.valevich.clean.presentation.presenters.base;

import com.valevich.clean.presentation.ui.base.BaseView;

public interface BasePresenter<T extends BaseView> {

    void onError(String message);

    void setView(T view);
}
