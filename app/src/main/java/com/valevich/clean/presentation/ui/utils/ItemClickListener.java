package com.valevich.clean.presentation.ui.utils;

public interface ItemClickListener<T> {
    void onItemClicked(T item);
    boolean onItemLongClicked(T item);
}
