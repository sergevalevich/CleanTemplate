package com.valevich.clean.presentation.ui.activities;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.valevich.clean.R;
import com.valevich.clean.presentation.presenters.impl.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusActivity;
import timber.log.Timber;

@RequiresPresenter(MainPresenter.class)
public class MainActivity extends NucleusActivity<MainPresenter> {

    @BindView(R.id.hello)
    TextView mWelcomeTextView;

    @BindView(R.id.input)
    EditText mEditText;

    private Unbinder mUnbinder;

    public void onMessageReceived(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        hideLoading();
        showMessage(message);
    }

    public void onError(Throwable error) {
        hideLoading();
        showError(error.getMessage());
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);


    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
    }

    @OnClick(R.id.task_one)
    void execTaskOne() {
        showLoading();
        String message = mEditText.getText().toString();
        Timber.d(message);
        getPresenter().loadHelloMessage(message);
    }

    @OnClick(R.id.task_two)
    void execTaskTwo() {
        showLoading();
        String message = mEditText.getText().toString();
        Timber.d(message);
        getPresenter().loadByeMessage(message);
    }

    private void showMessage(String message) {
        mWelcomeTextView.setText(message);
    }

    private void showError(String error) {
        mWelcomeTextView.setText(error);
    }

    private void showLoading() {
        mEditText.setTextColor(ContextCompat.getColor(this,R.color.colorAccent));
    }

    private void hideLoading() {
        mEditText.setTextColor(ContextCompat.getColor(this,android.R.color.holo_green_light));
    }
}
