package com.geekhub.palto.customviews;

import android.content.Context;
import android.location.GpsStatus;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 07.02.16.
 */
public class PolToCheckBoxList extends LinearLayout {
    ArrayList<String> listOfInterests = new ArrayList<>();
    HashMap<String, Boolean> stateMap = new HashMap<>();
    ArrayList<CheckBox> checkboxes = new ArrayList<>();

    public PolToCheckBoxList(Context context) {
        super(context);
    }

    public PolToCheckBoxList(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PolToCheckBoxList(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HashMap<String, Boolean> initializeMap(ArrayList<String> list){
        for (String key: list) {
            stateMap.put(key, false);
        }
        return stateMap;
    }

    public void initWithStateMap(HashMap<String, Boolean> savedStateMap){
        setOrientation(VERTICAL);
        if (savedStateMap==null) {
            savedStateMap=stateMap;
        }
            for (Map.Entry<String, Boolean> entry :
                    savedStateMap.entrySet()) {
                CheckBox checkBox = new CheckBox(getContext());
                checkBox.setChecked(entry.getValue());
                checkBox.setText(entry.getKey());
                addView(checkBox);
            }
     }

    public HashMap<String, Boolean> getStateMapFromCheckBoxes(){
        for (CheckBox c :
                checkboxes) {
            Log.d("list","key" + c.getText().toString() + " is" + c.isChecked());
            stateMap.put(c.getText().toString(), c.isChecked());
        }
        return stateMap;
    }
}
