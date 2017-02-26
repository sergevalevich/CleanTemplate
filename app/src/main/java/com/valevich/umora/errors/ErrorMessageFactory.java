package com.valevich.umora.errors;

import android.content.Context;

import com.valevich.umora.R;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class ErrorMessageFactory {

    public static String createErrorMessage(Context context, Throwable t) {
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