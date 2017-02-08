package com.valevich.clean.presentation.ui.utils;

public class DpToPixConverter {
    public static int dpToPix(int dp,float scale) {
        int padding_in_dp = 15;
        return (int) (padding_in_dp * scale + 0.5f);
    }
}
