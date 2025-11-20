package com.gazlaws.codeboard.layout.ui;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.gazlaws.codeboard.theme.UiTheme;

public class KeyboardLayoutView extends ViewGroup {

    private final UiTheme uiTheme;

    private int widthReduction = 0;

    public KeyboardLayoutView(Context context, UiTheme uiTheme) {
        super(context);
        setBackgroundColor(uiTheme.backgroundColor);
        this.uiTheme = uiTheme;
    }

    public void setWidthReduction(int reduction) {
        widthReduction = reduction;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();

        int availableHeight = metrics.heightPixels;
        int availableWidth = metrics.widthPixels - widthReduction;

        float keyboardSize = availableHeight > availableWidth
                ? uiTheme.portraitSize
                : uiTheme.landscapeSize;

        setMeasuredDimension(availableWidth, (int)(availableHeight * keyboardSize));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            child.layout(
                    l + getPaddingLeft(),
                    t + getPaddingTop(),
                    r - getPaddingRight(),
                    b - getPaddingBottom());
        }
    }

    public void applyShiftModifier(boolean shiftPressed){
        for (KeyboardButtonView button : getKeyboardButtons()){
             button.applyShiftModifier(shiftPressed);
        }
    }

    public void applyCtrlModifier(boolean ctrlPressed){
        for (KeyboardButtonView button : getKeyboardButtons()){
            button.applyCtrlModifier(ctrlPressed);
        }
    }

    private Collection<KeyboardButtonView> getKeyboardButtons(){
        int childCount = this.getChildCount();
        ArrayList<KeyboardButtonView> list = new ArrayList<>(childCount);
        for (int i=0; i<childCount; i++){
            View child = getChildAt(i);
            if (child instanceof KeyboardButtonView){
                list.add((KeyboardButtonView)child);
            }
        }
        return list;
    }
}
