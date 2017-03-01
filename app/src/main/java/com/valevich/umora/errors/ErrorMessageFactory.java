package com.valevich.umora.errors;

import android.content.Context;

import com.valevich.umora.R;
import com.valevich.umora.injection.ActivityContext;
import com.valevich.umora.injection.PerActivity;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import javax.inject.Inject;

@PerActivity
public class ErrorMessageFactory implements IErrorMessageFactory {

    private Context context;

    @Inject
    public ErrorMessageFactory (@ActivityContext Context context) {
        this.context = context;
    }

    @Override
    public String createErrorMessage(Throwable t) {
        String message = t.getMessage();
        if (t instanceof SocketTimeoutException)
            return context.getString(R.string.request_timeout);
        if (t instanceof NetworkUnavailableException || t instanceof UnknownHostException)
            return context.getString(R.string.no_net_error_message);
        if (t instanceof UpdateDeleteException)
            return context.getString(R.string.update_error_message);
        if(message == null || message.isEmpty())
            return context.getString(R.string.default_error_message);
        return message;
    }
}
