package com.geekhub.palto.customviews;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.geekhub.palto.R;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by andrey on 04.03.16.
 */
public class PalToChoicePicker extends LinearLayout {
    private TextView title;
    public TextView interests;
    private LinearLayout linearLayout;
    private ImageButton plus;
    private ImageView icon;
    private int maxItems;
    private boolean isSingleChoise;
    private String[] strings;
    private AlertDialog dialog;
    private boolean[] booleans;
    private ArrayList<String> items = new ArrayList<>();
    private final int MUSIK = 0;
    private final int FILM = 1;
    private final int ZNAK = 2;
    private final int GROWTH = 3;
    private final int EYES = 4;
    private Button dialogButton;

    public PalToChoicePicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0, 0);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PalToChoicePicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context, attrs, defStyleAttr, defStyleRes);
    }


    public PalToChoicePicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr, 0);
    }

    public PalToChoicePicker(Context context) {
        super(context);
        initView(context, null, 0, 0);
    }



    private void  initView(final Context context, AttributeSet attrs, final int defStyleAttr, int defStyleRes) {
        inflate(context, R.layout.custom_picker, this);
        this.title = (TextView) findViewById(R.id.titleCustom);
        this.icon = (ImageView) findViewById(R.id.iconinteres);
        this.plus = (ImageButton) findViewById(R.id.imageButtonPlus);
        this.interests = (TextView) findViewById(R.id.interest);
        this.linearLayout = (LinearLayout) findViewById(R.id.customPicker);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.PalToChoicePicker, defStyleAttr, defStyleRes);
            if (a.hasValue(R.styleable.PalToChoicePicker_aboutPicker)) {
                switch (a.getInt(R.styleable.PalToChoicePicker_aboutPicker,5)){
                    case (MUSIK):
                     icon.setImageResource(R.drawable.ic_music);
                        title.setText(R.string.musik);
                        maxItems = 5;
                        strings = context.getResources().getStringArray(R.array.musik);
                        interests.setText("Укажите от 1 до 5 жанров музыки");
                        break;
                    case (FILM):
                     icon.setImageResource(R.drawable.ic_film);
                        title.setText(R.string.film);
                        maxItems = 5;
                        strings = context.getResources().getStringArray(R.array.film);
                        interests.setText("Укажите от 1 до 5 жанров фильмов");
                        break;
                    case (ZNAK):
                        icon.setImageResource(R.drawable.ic_znak);
                        title.setText(R.string.znak);
                        maxItems = 3;
                        strings = context.getResources().getStringArray(R.array.znakom);
                        interests.setText("Укажите от 1 до 3 целей знакомства");
                        break;
                    case (GROWTH):
                        icon.setImageResource(R.drawable.ic_walk);
                        title.setText(R.string.growth);
                        maxItems = 1;
                        isSingleChoise = true;
                        strings = context.getResources().getStringArray(R.array.growth);
                        interests.setText("Укажите ваш рост");
                        break;
                    case (EYES):
                        icon.setImageResource(R.drawable.ic_eyes);
                        title.setText(R.string.eyes);
                        maxItems = 1;
                        isSingleChoise = true;
                        strings = context.getResources().getStringArray(R.array.eyes);
                        interests.setText("Укажите цвет ваших глаз");
                        break;}}
            booleans = new boolean[strings.length];
            linearLayout.setOnClickListener(new OnClickListener() {
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


        }
    }




    private void buildDialog(final Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final Animation animation = AnimationUtils.loadAnimation(context,R.anim.myscale);
        if (!isSingleChoise) {
            builder.setTitle(title.getText())
                    .setCancelable(false)
                    .setMultiChoiceItems(strings, booleans, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            booleans[which] = isChecked;
                            if(getInterestSet().size() > maxItems){
                                dialogButton.setEnabled(false);
                                Toast.makeText(context,"Максимально можно указать " + maxItems
                                        + " интересов!",Toast.LENGTH_LONG).show();
                            }else{
                                dialogButton.setEnabled(true);
                            }

                        }
                    }).setPositiveButton("Готово", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    interests.startAnimation(animation);
                    if(interestWhite(getInterestSet()).length()>0){
                    interests.setText(interestWhite(getInterestSet()));}

                    dialog.dismiss();
                }
            });

            builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (boolean b : booleans) {
                        b = false;
                    }
                    interests.startAnimation(animation);

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
            builder.setTitle(title.getText()).
                    setSingleChoiceItems(strings, choice, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (boolean b : booleans) {
                                b = false;
                            }
                            booleans[which] = true;
                            interests.startAnimation(animation);
                            if(interestWhite(getInterestSet()).length()>0){
                            interests.setText(interestWhite(getInterestSet()));}
                            dialog.dismiss();
                        }
                    }).setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    for (boolean b :
                            booleans) {
                        b = false;
                    }
                    dialog.dismiss();
                }
            });
        }

        dialog = builder.create();
    }

    public String interestWhite(ArrayList<String> arrayList){
    String interest = "";
        for(int i = 0; i<arrayList.size();i++){
            if(arrayList.get(i)!=null){
            if(interest.length()>0){
                interest = interest + ", " + arrayList.get(i);
            }else{
                interest = arrayList.get(i);
            }
        }}
    return  interest;}
    public ArrayList<String> getInterestSet(){
        ArrayList<String> set = new ArrayList<>();
        String[] listToAdd = strings;

        if(strings != null){
            for (int i=0;i<strings.length;i++){
                if (booleans[i]) set.add(listToAdd[i]);
            }}
        return set;
    }
    public void setInterest(ArrayList<String> arrayList){
        interests.setText(interestWhite(arrayList));
        for (int i=0;i<strings.length;i++){
            if(arrayList.contains(strings[i])){
                booleans[i] = true;
            }
        }
    }
}
