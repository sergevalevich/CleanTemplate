package com.valevich.clean;


import com.valevich.clean.domain.executor.impl.ThreadExecutor;
import com.valevich.clean.domain.interactors.IWelcomingInteractor;
import com.valevich.clean.domain.interactors.impl.WelcomingInteractor;
import com.valevich.clean.domain.repository.IMessageRepository;
import com.valevich.clean.domain.repository.impl.MessageRepository;
import com.valevich.clean.threading.MainThread;

import org.junit.Test;
import org.junit.runner.RunWith;


import timber.log.Timber;



/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
//@RunWith(MockitoJUnitRunner.class)
public class ExampleInstrumentedTest {

    IMessageRepository mMessageRepository = new MessageRepository();
    IWelcomingInteractor.Callback mMockedCallback = new IWelcomingInteractor.Callback() {
        @Override
        public void onMessageRetrieved(String message) {
            Timber.d("Retrieved message %s",message);
        }

        @Override
        public void onRetrievalFailed(String error) {
            Timber.d("Error retrieving message %s",error);
        }
    };

    @Test
    public void testInteractor() throws Exception {
//        String msg = "Welcome, friend!";
//
//        when(mMessageRepository.getMessage())
//                .thenReturn(msg);
//
//        IWelcomingInteractor interactor = new WelcomingInteractor(
//                ThreadExecutor.getInstance(),
//                MainThread.getInstance(),
//                mMockedCallback,
//                mMessageRepository
//        );
//        interactor.execute();
//        Mockito.verify(mMessageRepository).getMessage();
//        Mockito.verifyNoMoreInteractions(mMessageRepository);
//        Mockito.verify(mMockedCallback).onMessageRetrieved(msg);
    }
}
