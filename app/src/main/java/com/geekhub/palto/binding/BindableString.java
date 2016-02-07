package com.geekhub.palto.binding;

import android.databinding.BaseObservable;
import android.support.annotation.NonNull;

public class BindableString extends BaseObservable {

    private String value;
    public boolean isValid = false;

    // Function to get the value
    public String get() {
        return value != null ? value : "";
    }

    // Function to set the value
    public void set(@NonNull String value) {
        if (!value.equals(this.value)) {
            this.value = value;
            notifyChange();
        }
    }

}