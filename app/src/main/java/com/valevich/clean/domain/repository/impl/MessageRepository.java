package com.valevich.clean.domain.repository.impl;

import com.valevich.clean.domain.repository.IMessageRepository;

public class MessageRepository implements IMessageRepository {
    @Override
    public String getMessage() {
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Welcome, friend!";
    }
}
