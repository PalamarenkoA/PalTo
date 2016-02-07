package com.geekhub.palto.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekhub.palto.R;


/**
 * Created by trinity on 11/10/15.
 */

public class MyReasonPicker extends LinearLayout {


    private AppCompatTextView mLabel;
    private AppCompatTextView mTitle;

    private String parameter = "";

    private AlertDialog pickerDialog;
    private WindowManager.LayoutParams lp;
    private PickerReasonAdapter pickerAdapter;

    public MyReasonPicker(Context context) {
        super(context);
        initViews(context, null, 0, 0);
    }

    public MyReasonPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews(context, attrs, 0, 0);
    }

    public MyReasonPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initViews(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyReasonPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initViews(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        pickerAdapter = new PickerReasonAdapter();

        setOrientation(VERTICAL);

        mLabel = new AppCompatTextView(context);

        mLabel.setTextAppearance(getContext(), R.style.LightTextLabelStyle);

        int lpad = 15;

        mLabel.setPadding(lpad, lpad, lpad, lpad);

        LinearLayout mainView = new LinearLayout(context);
        mainView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mainView.setBackgroundResource(R.drawable.bg_my_picker_normal);
        mainView.setOrientation(HORIZONTAL);

        int p = 15;
        mainView.setPadding(15, p, 15, p);

        mainView.setGravity(Gravity.CENTER_VERTICAL);

        mTitle = new AppCompatTextView(context);
        mTitle.setLayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1.0f));

        mTitle.setTextColor(getResources().getColorStateList(R.color.my_picker_state_list));

        mTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17.0f);


        ImageView icon = new ImageView(context);

        int icH = 15;

        icon.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, icH));
        icon.setScaleType(ImageView.ScaleType.FIT_CENTER);
        icon.setImageResource(R.drawable.ic_grey_arrow);
        icon.setAdjustViewBounds(true);

        mainView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                if (pickerAdapter != null && pickerAdapter.getList().size() > 0) {
                    pickerDialog.show();
                    pickerDialog.getWindow().setAttributes(lp);
                    pickerDialog.getListView().setPadding(0, 0, 0, 0);
                    pickerDialog.getListView().setOverScrollMode(View.OVER_SCROLL_NEVER);
                }
            }
        });

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.NeruFiltersPicker, defStyleAttr, defStyleRes);
            setLabelText(a.getString(R.styleable.NeruFiltersPicker_labelText));
            setLabelVisibility(a.getBoolean(R.styleable.NeruFiltersPicker_labelVisibility, false));
            mTitle.setText(a.getString(R.styleable.NeruFiltersPicker_defaultText));
            parameter = a.getString(R.styleable.NeruFiltersPicker_parameterName);
            a.recycle();
        }

        createPicker();

        mainView.addView(mTitle);

        mainView.addView(icon);

        addView(mLabel);
        addView(mainView);
        mTitle.setText(getResources().getString(R.string.select));
        mLabel.setText(getResources().getString(R.string.your_reason));
        select(0);
    }

    public void setLabelText(String mLabelText) {
        this.mLabel.setText(mLabelText);
    }

    public void setTitleText(String mTitleText) {
        this.mTitle.setText(mTitleText);
    }

    private void createPicker() {
        TextView titleView = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.dialog_title, null);

        titleView.setText(getResources().getString(R.string.select));

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getContext(), R.style.PickerDialogTheme);

        builder
                .setCustomTitle(titleView)
                .setSingleChoiceItems(pickerAdapter, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        select(which);

                        dialog.dismiss();
                    }
                })
                .setCancelable(true);

        pickerDialog = builder.create();

        lp = new WindowManager.LayoutParams();
        lp.copyFrom(pickerDialog.getWindow().getAttributes());

        lp.width = Double.valueOf(getResources().getDisplayMetrics().widthPixels * 0.85).intValue();
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;//Double.valueOf(getResources().getDisplayMetrics().heightPixels * 0.85).intValue();
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lp.dimAmount = 0.6f ;

    }

    private void select(int which) {

        setTitleText(
                pickerAdapter.getList().get(which)
        );
        pickerAdapter.setSelected(which);

        mTitle.setTextColor(getResources().getColor(R.color.gray_dark));
        setLabelVisibility(true);

    }

    public void setLabelVisibility(boolean vis) {
        if (vis)
            mLabel.setVisibility(VISIBLE);
        else
            mLabel.setVisibility(GONE);
    }

    public String getSelectedReason(){
        return pickerAdapter.getItem(pickerAdapter.getSelected());
    }
}
