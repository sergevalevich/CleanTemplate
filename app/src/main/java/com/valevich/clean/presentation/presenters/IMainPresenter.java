package com.valevich.clean.presentation.presenters;


import com.valevich.clean.presentation.presenters.base.IRecoverablePresenter;
import com.valevich.clean.presentation.ui.MainView;

public interface IMainPresenter<T extends MainView> extends IRecoverablePresenter<T> {

    // TODO: Add your presenter methods
    void setView(T view);

    void login(String login,String password);

}
