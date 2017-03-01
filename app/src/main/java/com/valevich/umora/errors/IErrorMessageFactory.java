package com.valevich.umora.errors;

public interface IErrorMessageFactory {
    String createErrorMessage(Throwable t);
}
