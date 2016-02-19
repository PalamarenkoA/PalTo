package com.geekhub.palto.binding;

import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by trinity on 11/12/15.
 */
public class BindableBoolean extends BaseObservable implements Parcelable, Serializable {
    static final long serialVersionUID = 1L;
    private boolean mValue;

    /**
     * Creates an ObservableBoolean with the given initial value.
     *
     * @param value the initial value for the ObservableBoolean
     */
    public BindableBoolean(boolean value) {
        mValue = value;
    }

    /**
     * Creates an ObservableBoolean with the initial value of <code>false</code>.
     */
    public BindableBoolean() {
    }

    /**
     * @return the stored value.
     */
    public boolean get() {
        return mValue;
    }

    /**
     * Set the stored value.
     */
    public void set(boolean value) {
        if (value != mValue) {
            mValue = value;
            notifyChange();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mValue ? 1 : 0);
    }

    public static final Creator<BindableBoolean> CREATOR
            = new Creator<BindableBoolean>() {

        @Override
        public BindableBoolean createFromParcel(Parcel source) {
            return new BindableBoolean(source.readInt() == 1);
        }

        @Override
        public BindableBoolean[] newArray(int size) {
            return new BindableBoolean[size];
        }
    };
}