package com.geekhub.palto.customviews;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.geekhub.palto.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Created by root on 10.02.16.
 */
public class PolToMultipleChoicePicker extends LinearLayout {

    private TextView mLabelTv;
    private Button button;
    private ArrayList<String> items = new ArrayList<>();
    private String[] strings;
    private boolean[] booleans;
    private AlertDialog dialog;

    public PolToMultipleChoicePicker(Context context) {
        super(context);
        initView(context, null, 0, 0);
    }

    public PolToMultipleChoicePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0,0);
    }

    public PolToMultipleChoicePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PolToMultipleChoicePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs, defStyleAttr, defStyleRes);
    }

    private void  initView(final Context context, AttributeSet attrs, final int defStyleAttr, int defStyleRes){
        setOrientation(VERTICAL);
        mLabelTv = new AppCompatTextView(context);
        mLabelTv.setPadding(5, 5, 5, 5);
        button = new Button(context);
        button.setPadding(25, 25, 25, 25);
        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PolToMultipleChoicePicker, defStyleAttr, defStyleRes);
            if (a.hasValue(R.styleable.PolToMultipleChoicePicker_pickerLabel)) {
                setLabelText(a.getString(R.styleable.PolToMultipleChoicePicker_pickerLabel));
            } else {
                setLabelText("Интересы");
            }
            if (a.hasValue(R.styleable.PolToMultipleChoicePicker_buttonTitle)) {
                setButtobnTitle(a.getString(R.styleable.PolToMultipleChoicePicker_buttonTitle));
            } else {
                setLabelText("Выюрать");
            }
        }
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buildDialog(context);
                dialog.show();
            }
        });

        addView(mLabelTv);
        addView(button);
    }

    private void setLabelText(String text){
        mLabelTv.setText(text);
    }

    private void setButtobnTitle(String text){
        button.setText(text);
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
        initCharSequences(items);
    }

    private String[] initCharSequences(ArrayList<String> list){
        strings=new String[list.size()];
        booleans = new boolean[list.size()];
        for (int i=0;i<list.size();i++){
            strings[i]=list.get(i);

            //TODO: Fill 'if' restore from saved instance
            if (true){
                booleans[i]=false;
            } else {
                booleans[i]=true;
            }
        }
        return strings;
    }

    private void buildDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(mLabelTv.getText())
                .setMultiChoiceItems(strings, booleans, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        booleans[which] = isChecked;
                    }
                }).setPositiveButton("Готово", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog = builder.create();
    }

    public HashSet<String> getInterestSet(){
        HashSet<String> set = new HashSet<>();
        for (int i=0;i<strings.length;i++){
            if (booleans[i]) set.add(strings[i]);
        }

        return set;
    }
 }
