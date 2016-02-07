package com.geekhub.palto.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.geekhub.palto.R;


/**
 * Created by trinity on 11/18/15.
 */
public class LoadingView extends LinearLayout {
    public LoadingView(Context context) {
        super(context);
        initViews(context, null, 0, 0);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs, 0, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initViews(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

         setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.HORIZONTAL);
        setGravity(Gravity.CENTER);

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.my_progress, this, true);

    }

    public void setLoading(boolean isL) {
        setVisibility(isL ? VISIBLE : GONE);
    }

    public void setLayoutGravity(int gravity) {
        setGravity(gravity);
    }

}
