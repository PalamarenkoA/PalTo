package com.geekhub.palto.customviews.splash;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.geekhub.palto.R;
import com.geekhub.palto.util.LayoutUtils;

import java.util.ArrayList;

/**
 * Created by duke0808 on 25.03.16.
 */
public class OleniDotPageIndicator extends LinearLayout {
    ArrayList<FrameLayout> list = new ArrayList<>();
    int selected = 0;

    public OleniDotPageIndicator(Context context) {
        super(context);
        initViews(context, null, 0, 0);
    }

    public OleniDotPageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs, 0, 0);
    }

    public OleniDotPageIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs, defStyleAttr, 0);
    }

    public OleniDotPageIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initViews(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.OleniDotPageIndicator, defStyleAttr, defStyleRes);
        }
        setOrientation(HORIZONTAL);

        for (int i = 0; i < 5; i++) {
            FrameLayout dot = getDot(context);
            addView(dot);
            list.add(dot);
        }
    }

    private FrameLayout getDot(Context context) {
        FrameLayout frameLayout = new FrameLayout(context);
        frameLayout.setBackground(context.getResources().getDrawable(R.drawable.circle_shape));
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        int marginPix = LayoutUtils.dpToPixels(6, context);
        params.setMargins(marginPix, 0, marginPix, 0);
        frameLayout.setLayoutParams(params);
        frameLayout.setSelected(false);
        return frameLayout;
    }

    public void setSelected(int selected) {
        list.get(this.selected).setSelected(false);
        this.selected = selected;
        list.get(selected).setSelected(true);
    }

    public void nextDot() {
        int i = selected + 1;
        if (i == 5) i = 0;
        setSelected(i);
    }

    public void prevDot() {
        int i = selected - 1;
        if (i == -1) i = 4;
        setSelected(i);
    }
}
