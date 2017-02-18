package com.valevich.clean.errors;

import android.content.Context;

import com.valevich.clean.R;

import java.net.SocketTimeoutException;

public class ErrorMessageFactory {

    public static String createErrorMessage(Context context, Throwable t) {
        String message = t.getMessage();
        if (t instanceof SocketTimeoutException)
            return context.getString(R.string.request_timeout);
        if (t instanceof NetworkUnavailableException)
            return context.getString(R.string.no_net_error_message);
        if (t instanceof UpdateDeleteException)
            return context.getString(R.string.update_error_message);
        if (t instanceof InsertException)
            return context.getString(R.string.insert_error_message);
        if(message == null || message.isEmpty())
            return context.getString(R.string.default_error_message);
        return message;
    }
}
