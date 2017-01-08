package com.valevich.clean.domain.interactors;
import com.valevich.clean.domain.interactors.base.Interactor;

public interface IWelcomingInteractor extends Interactor {

    //result Handler//Presenter
    interface Callback {
        void onMessageRetrieved(String message);

        void onRetrievalFailed(String error);
    }

    // TODO: Add interactor methods here
}
