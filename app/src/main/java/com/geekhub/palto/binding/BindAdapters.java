package com.geekhub.palto.binding;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.geekhub.palto.R;
import com.geekhub.palto.customviews.MaterialEditText;

import de.hdodenhof.circleimageview.CircleImageView;

public class BindAdapters {

    @BindingAdapter({"app:imageUrl"})
    public static void bindImageUrl(ImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).placeholder(R.drawable.imgpsh_fullsize)
                .error(R.drawable.imgpsh_fullsize).into(imageView);
    }

    @BindingAdapter({"app:circleImage"})
    public static void bindCircleImageUrl(CircleImageView imageView, String url) {
        Glide.with(imageView.getContext()).load(url).placeholder(R.drawable.imgpsh_fullsize)
                .dontAnimate().dontTransform()
                .error(R.drawable.imgpsh_fullsize).into(imageView);
    }

    @BindingAdapter({"app:htmlText"})
    public static void bindHtml(TextView view, String text) {
        if (text != null && text.length() > 0)
            view.setText(Html.fromHtml(text));

    }

    @BindingAdapter({"app:scrollerAdapter"})
    public static void bindScrollerAdapter(ViewPager list, PagerAdapter adapter) {
        Log.e("search", "bind adapter");
        list.setAdapter(adapter);
    }

    @BindingAdapter({"app:bindCheckBoxState"})
    public static void bindCheckBox(final AppCompatCheckBox view, final BindableBoolean observableBoolean) {
        if (view.getTag(R.id.dataBinding) == null) {
            view.setTag(R.id.dataBinding, true);
            view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    observableBoolean.set(isChecked);
                }
            });
        }
        boolean newValue = observableBoolean.get();
        if (view.isChecked() != newValue) {
            view.setChecked(newValue);
        }
    }

    @BindingAdapter({"app:bindingWithOnFocusValidation"})
    public static void bindValidation(final MaterialEditText view, final BindableString bindableString) {

        if (view.getTag(R.id.dataBinding) == null) {
            view.setTag(R.id.dataBinding, true);

            final Runnable check = new Runnable() {
                @Override
                public void run() {
                    view.validate();
                }
            };

            view.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    view.removeCallbacks(check);
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    bindableString.isValid = view.silentValidate();
                    bindableString.set(s.toString());
                    if (view.isErrorEnabled()) {
                        view.setErrorText("");
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                    view.postDelayed(check, 1200);
                }
            });


        }

        String newValue = bindableString.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }

    }

    @BindingAdapter({"app:textBinding"})
    public static void bindEditText(final EditText view, final BindableString bindableString) {
        // We use tag to ensure that we aren't adding multiple TextWatcher for same EditText. This ensures that
        // EditText has only one TextWatcher


        if (view.getTag(R.id.dataBinding) == null) {
            view.setTag(R.id.dataBinding, true);

            view.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    bindableString.set(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });


        }

        String newValue = bindableString.get();
        if (!view.getText().toString().equals(newValue)) {
            view.setText(newValue);
        }

    }
}