package com.valevich.clean.presentation.ui.activities;

import android.os.Bundle;
import android.widget.TextView;

import com.valevich.clean.R;
import com.valevich.clean.domain.executor.impl.ThreadExecutor;
import com.valevich.clean.presentation.presenters.IMainPresenter;
import com.valevich.clean.presentation.presenters.impl.MainPresenter;
import com.valevich.clean.presentation.ui.MainView;
import com.valevich.clean.presentation.ui.base.BaseActivity;
import com.valevich.clean.threading.MainThread;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import timber.log.Timber;

public class MainActivity extends BaseActivity implements MainView {

    @BindView(R.id.hello)
    TextView mWelcomeTextView;

    private static IMainPresenter<MainView> mPresenter;

    private Unbinder mUnbinder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);

        if (mPresenter == null) {
            mPresenter = new MainPresenter(ThreadExecutor.getInstance(), MainThread.getInstance(),this);
            if (savedInstanceState != null) {
                mPresenter.restore(savedInstanceState);
            } else {
                mPresenter.login("testLogin","testPassword");
            }
        } else {
            mPresenter.setView(this);
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.setView(null);
        mUnbinder.unbind();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mPresenter.save(outState);
        Timber.d("ONSAVEINSTANCESTATE");
    }

    @Override
    public void displayMessage(String message) {
        mWelcomeTextView.setText(message);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }
}
