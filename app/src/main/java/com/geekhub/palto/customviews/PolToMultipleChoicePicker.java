package com.geekhub.palto.customviews;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geekhub.palto.R;
import com.geekhub.palto.object.Item;
import com.geekhub.palto.object.VKCItiesResponse;

import com.google.gson.Gson;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class PolToMultipleChoicePicker extends LinearLayout {

    private TextView mLabelTv;
    private Button button;
    private ArrayList<String> items = new ArrayList<>();
    private String[] strings;
    private boolean[] booleans;
    private AlertDialog dialog;
    private Context context;
    private boolean isSingleChoise;
    private String myMessage;
    private Button dialogButton;
    private int maxItems;
    private boolean getIds = false;
    private ArrayList<String> idList;
    private int colorNormalState;

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
        setOrientation(HORIZONTAL);
        this.context = context;
        mLabelTv = new AppCompatTextView(this.context);
        mLabelTv.setPadding(5, 5, 5, 5);
        LinearLayout.LayoutParams paramsLabel = new LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsLabel.weight=1.1f;
        paramsLabel.gravity=Gravity.CENTER;
        mLabelTv.setLayoutParams(paramsLabel);
        mLabelTv.setTextSize(20);
        button = new Button(context);
        button.setPadding(15, 15, 15, 15);

        LinearLayout.LayoutParams paramsBt = new LayoutParams(300, ViewGroup.LayoutParams.WRAP_CONTENT);
        paramsBt.gravity = Gravity.RIGHT;
        paramsBt.setMargins(5, 5, 5, 5);
        paramsBt.weight=0.1f;
        colorNormalState = context.getResources().getColor(R.color.colorPrimary);
        button.setBackgroundColor(colorNormalState);
        button.setLayoutParams(paramsBt);

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
                setButtobnTitle("Выбрать");
            }
            isSingleChoise = a.getBoolean(R.styleable.PolToMultipleChoicePicker_isSingleChoisePicker, false);
            if (a.hasValue(R.styleable.PolToMultipleChoicePicker_my_message)){
                myMessage = a.getString(R.styleable.PolToMultipleChoicePicker_my_message);
            }
            maxItems = a.getInt(R.styleable.PolToMultipleChoicePicker_maxItems, 5);
        }
        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                buildDialog(context);
                dialog.show();
                dialogButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
                if (dialogButton!=null) {
                    if (getInterestSet().size() > maxItems) {
                        dialogButton.setEnabled(false);
                    } else {
                        dialogButton.setEnabled(true);
                    }
                }
            }
        });

        LinearLayout.LayoutParams mainParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(mainParams);
        addView(mLabelTv);
        addView(button);
    }

    private void setLabelText(String text){
        mLabelTv.setText(text);
    }
    private void setButtobImage(int i){

    }
    private void setButtobnTitle(String text){
        button.setText(text);
    }
    public void setButtonUsed(boolean isUsed){
        if (isUsed){
            button.setBackgroundColor(context.getResources().getColor(R.color.colorAccent));
        } else {
            button.setBackgroundColor(colorNormalState);
        }
    }
    public void setItemsFromResource (String [] mas){
        ArrayList <String> items = new ArrayList<>();
        Collections.addAll(items, mas);
        this.items = items;
        initCharSequences(items);
    }
    public void setItems(ArrayList<String> items) {
        this.items = items;
        initCharSequences(items);
    }

    public void setItemsFromVKRequest(final VKRequest request){
        button.setEnabled(false);
        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                Gson gson = new Gson();
                VKCItiesResponse vkcItiesResponse = gson.fromJson(String.valueOf(response.json), VKCItiesResponse.class);
                List<Item> items = vkcItiesResponse.getResponse().getItems();
                ArrayList<Item> list = new ArrayList<Item>();
                list.addAll(items);

                idList = new ArrayList<String>();
                ArrayList<String> nameList = new ArrayList<String>();

                for (Item i :
                        list) {
                    idList.add(i.getId().toString());
                    nameList.add(i.getTitle());
                }

                setItems(nameList);
                getIds = true;
                button.setEnabled(true);
            }

            @Override
            public void attemptFailed(VKRequest request, int attemptNumber, int totalAttempts) {
                super.attemptFailed(request, attemptNumber, totalAttempts);
            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
            }

            @Override
            public void onProgress(VKRequest.VKProgressType progressType, long bytesLoaded, long bytesTotal) {
                super.onProgress(progressType, bytesLoaded, bytesTotal);
            }
        });
    }
    private String[] initCharSequences(ArrayList<String> list){
        strings=new String[list.size()];
        booleans = new boolean[list.size()];
        for (int i=0;i<list.size();i++){
            strings[i]=list.get(i);

            //TODO: Fill 'if' restore from saved instance
            booleans[i]=false;
        }
        return strings;
    }
    private void buildDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if (!isSingleChoise) {
            builder.setTitle(mLabelTv.getText())
                    .setCancelable(false)
                    .setMultiChoiceItems(strings, booleans, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            booleans[which] = isChecked;
                            if (getInterestSet().size() > maxItems) {
                                dialogButton.setEnabled(false);
                            } else {
                                dialogButton.setEnabled(true);
                            }
                        }
                    }).setPositiveButton("Готово", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String toast = "";
                    int size = getInterestSet().size();
                    for(int i = 0;i< size;i++) {
                        if (toast.equals("")) {
                            toast =getInterestSet().get(i);
                        }else{
                        toast = toast + ", " + getInterestSet().get(i);}
                    }
                    Toast.makeText(context,"Вы выбрали " + toast,Toast.LENGTH_LONG).show();
                    if (size >0){
                        setButtobnTitle(String.format("Выбрано: %d", size));
                        setButtonUsed(true);
                    } else {
                        setButtobnTitle("Выбрать");
                        setButtonUsed(false);
                    }
                    dialog.dismiss();
                }
            });
            if (myMessage!=null) {
                builder.setTitle(myMessage);
            }
            builder.setNegativeButton("Не указывать", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (boolean b :
                            booleans) {
                        b = false;
                    }
                    setButtobnTitle("Не указано");
                    setButtonUsed(false);
                    dialog.dismiss();
                }
            });
        } else {
            int choice = 0;
            for(int i = 0;i<booleans.length;i++){
                if(booleans[i]){
                    choice =i;
                }

            }
            builder.setTitle(mLabelTv.getText()).
                    setSingleChoiceItems(strings, choice, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (boolean b :
                                    booleans) {
                                b = false;
                            }
                            booleans[which] = true;
                            setButtonUsed(true);
                            setButtobnTitle(strings[which]);
                            dialog.dismiss();
                        }
                    }).setNegativeButton("Не указывать", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (boolean b :
                            booleans) {
                        b = false;
                    }
                    setButtobnTitle("Не указано");
                    setButtonUsed(false);
                    dialog.dismiss();
                }
            });
        }

        dialog = builder.create();
    }
    public void setInterest(ArrayList<String> arrayList){
        for (int i=0;i<strings.length;i++){
            if(arrayList.contains(strings[i])){
                booleans[i] = true;
            }
        }
    }
    public ArrayList<String> getInterestSet(){
        ArrayList<String> set = new ArrayList<>();
        String[] listToAdd;
        if (getIds){
            String[] ss = new String[idList.size()];
            for (int i=0; i<idList.size();i++){
                ss[i]=idList.get(i);
            }
            listToAdd=ss;
        } else {
            listToAdd = strings;
        }
        if(strings != null){
        for (int i=0;i<strings.length;i++){
            if (booleans[i]) set.add(listToAdd[i]);
        }}
        return set;
    }
 }
