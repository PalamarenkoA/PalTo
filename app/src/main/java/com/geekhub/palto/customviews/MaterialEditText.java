package com.geekhub.palto.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import com.geekhub.palto.R;

import com.geekhub.palto.util.TextUtil;

import java.util.regex.Pattern;


/**
 * Created by trinity on 12/2/15.
 */
public class MaterialEditText extends LinearLayout {

    private int minLines = 1;
    private boolean needValidation=true;

    public enum ValidationType {
        NON(0), EMAIL(1), PERSON(2), PASSWORD(3), CONFIRM(4), SUBDOMAIN(5);

        private final int value;

        private ValidationType(int value) {
            this.value = value;
        }

        public int id() {
            return value;
        }

        static ValidationType fromId(int id) {
            for (ValidationType f : values()) {
                if (f.id() == id) return f;
            }
            throw new IllegalArgumentException();
        }
    }

    private AppCompatTextView label;
    private AppCompatEditText mEditText;
    private AppCompatTextView error;
    public static final CharSequence STRING_EMPTY_FIELD = "Это поле не может быть пустым";
    private static final CharSequence STRING_UNCORRECT_EMAIL = "E-mail должен иметь вид \"example@domain.com\"";
    private static final CharSequence STRING_UNCORRECT_PASSWORD = "Пароль должен иметь от 6 до 20 символов";
    private static final CharSequence STRING_UNCORRECT_PERSON_NAME = "Имя должно иметь от 3 до 50 символов";
    private static final CharSequence STRING_PASSWORD_IS_NOT_EQUAL = "Пароли не совпадают";

    private final Pattern EMAIL_PATTERN
            = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

    private final Pattern PASSWORD_PATTERN
            = Pattern.compile("^[A-Za-z0-9]{6,20}$");

    private final Pattern PERSON_NAME_PATTERN
            = Pattern.compile("^[\\w]{3,50}$");

    //    private int confirmToField = -1;
    private boolean isValid = false;
    private int errorBackgroundId;
    private int mainBackGround;
    private ValidationType validationType = ValidationType.NON;
    Context context;

    public MaterialEditText(Context context) {
        super(context);
        this.context=context;
        initViews(context, null, 0, 0);

    }

    public MaterialEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initViews(context, attrs, 0, 0);

    }

    public MaterialEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initViews(context, attrs, defStyleAttr, 0);

    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MaterialEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initViews(context, attrs, defStyleAttr, defStyleRes);

    }

    private void initViews(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {

        setOrientation(VERTICAL);

        if (attrs != null) {

            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MaterialEditText, defStyleAttr, defStyleRes);

            errorBackgroundId = a.getResourceId(R.styleable.MaterialEditText_editTextErrorBackground, 0);
            mainBackGround = a.getResourceId(R.styleable.MaterialEditText_android_background, 0);
            validationType = ValidationType.fromId(a.getInt(R.styleable.MaterialEditText_validationType, ValidationType.NON.id()));

//            confirmToField = a.getInt(R.styleable.MaterialEditText_confirmToField, -1);
//
//            Log.e("material edittext", "confirmfield - " + confirmToField);
//            Log.e("material edittext", "confirmfield2 - " + a.getInteger(R.styleable.MaterialEditText_confirmToField, -1));

            needValidation=a.getBoolean(R.styleable.MaterialEditText_needValidation, true);

            initLabel(a);

            initEditText(a);

            initErrorLabel();

            a.recycle();
        }
    }


    private void initLabel(TypedArray a) {
        label = new AppCompatTextView(getContext());

        label.setTextAppearance(getContext(), R.style.LightTextLabelStyle);

        int lpad = 15;

        label.setPadding(lpad, 0, lpad, lpad);

        label.setText(a.getString(R.styleable.MaterialEditText_label));

        if (!a.hasValue(R.styleable.MaterialEditText_label))
            label.setVisibility(GONE);

        addView(label);

    }

    private void initEditText(TypedArray a) {
        mEditText = new AppCompatEditText(getContext());
        mEditText.setTypeface(Typeface.DEFAULT);
        mEditText.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        mEditText.setBackgroundResource(mainBackGround);

        setBackgroundResource(0);

        mEditText.setInputType(a.getInt(R.styleable.MaterialEditText_android_inputType, InputType.TYPE_TEXT_VARIATION_PERSON_NAME));
        minLines = a.getInt(R.styleable.MaterialEditText_android_minLines, 1);

        mEditText.setMinLines(minLines);

        int p = 10;
        int pl = p;
        if (a.hasValue(R.styleable.MaterialEditText_textPaddingLeft)){
            pl = a.getLayoutDimension(R.styleable.MaterialEditText_textPaddingLeft, p);
        }
        mEditText.setPadding(pl, p, p, p);

        if (minLines > 1) {

            mEditText.setGravity(Gravity.TOP | Gravity.LEFT);

            mEditText.setVerticalScrollBarEnabled(true);
        } else {

            if (a.hasValue(R.styleable.MaterialEditText_android_lines))
                mEditText.setLines(a.getInt(R.styleable.MaterialEditText_android_lines, 1));

        }

        mEditText.setImeOptions(a.getInt(R.styleable.MaterialEditText_android_imeOptions, EditorInfo.IME_NULL));
        //   mEditText.setGravity(a.getInt(R.styleable.MaterialEditText_android_gravity, Gravity.LEFT | Gravity.CENTER_VERTICAL));

//        if (a.hasValue(R.styleable.MaterialEditText_android_maxLength)) {
        int maxLength = 100;
        if (a.hasValue(R.styleable.MaterialEditText_maxLength)) {
            maxLength = a.getInt(R.styleable.MaterialEditText_maxLength,100);
        }
        InputFilter[] filterArray = new InputFilter[1];
        filterArray[0] = new InputFilter.LengthFilter(maxLength);
        mEditText.setFilters(filterArray);
//        }

        mEditText.setHintTextColor(a.getColor(R.styleable.MaterialEditText_hintColor, Color.WHITE));
        mEditText.setTextColor(a.getColor(R.styleable.MaterialEditText_android_textColor, Color.WHITE));

        if (!a.hasValue(R.styleable.MaterialEditText_label))
            mEditText.setHint(a.getString(R.styleable.MaterialEditText_android_hint));

        addView(mEditText);
    }

    private void initErrorLabel() {
        error = new AppCompatTextView(getContext());

        error.setTextAppearance(getContext(), R.style.ErrorTextLabelStyle);

//        int lpad = getResources().getDimensionPixelSize(R.dimen.label_padding);

        error.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14.0f);

        error.setPadding(0, 0, 0, 0);

        setErrorEnabled(false);

        addView(error);

    }

    public void setLabel(String text) {
        label.setText(text);
    }

    public void setErrorText(String text) {
        error.setText(text);
    }

    public void setErrorEnabled(boolean en) {
            if (en)
                error.setVisibility(VISIBLE);
            else
                error.setVisibility(GONE);
    }

    public boolean isErrorEnabled() {
        return error.getVisibility() != GONE;
    }

    public void addTextChangedListener(TextWatcher watcher) {
        mEditText.addTextChangedListener(watcher);
    }

    public boolean validate() {

        if (isEmpty()) {
            return isValid = false;
        }

//        if (confirmToField != -1) {
//            isValid = confirmToField();
//            return isValid;
//        }

        switch (validationType) {
            case EMAIL:
                return isValid = isEmailField();
            case PERSON:
                return isValid = isPersonNameField();
            case PASSWORD:
                return isValid = isPasswordField();
            case CONFIRM:
                return isValid = confirmToField(false);
            default:
                return isValid = !isEmpty();
        }

    }

    public boolean silentValidate() {

        if (isEmptySilent()) {
            return isValid = false;
        }

        switch (validationType) {
            case EMAIL:
                return isValid = isEmail();
            case PERSON:
                return isValid = isPersonName();
            case PASSWORD:
                return isValid = isPassword();
            case CONFIRM:
                return isValid = confirmToField(true);
            default:
                return isValid = !isEmptySilent();
        }

    }

    public boolean isEmailField() {
        String email = getText().toString().trim();

        if (isEmpty()) {
            return false;
        } else if (isEmail(email)) {
            setInternalError("");
            return true;
        } else {
            setInternalError(STRING_UNCORRECT_EMAIL);
            return false;
        }
    }

    public boolean isPasswordField() {
        String password = getText().toString().trim();

        if (isEmpty()) {

        } else if (isPassword(password)) {
            setInternalError("");
            return true;
        } else {
            setInternalError(STRING_UNCORRECT_PASSWORD);

        }
        return false;
    }

    public boolean isPersonNameField() {
        String password = getText().toString().trim();

        if (isEmpty()) {

        } else if (isPersonName(password)) {
            setInternalError("");
            return true;
        } else {
            setInternalError(STRING_UNCORRECT_PERSON_NAME);

        }
        return false;
    }

    public void setInternalError(@NonNull CharSequence error) {

        if (needValidation) {
            if (!error.toString().equals(getErrorText())) {

                if (!TextUtil.isEmpty(error)) {
                    setErrorEnabled(true);
                    setErrorText(error.toString());
                    mEditText.setBackgroundResource(this.errorBackgroundId);
                } else {
                    setErrorEnabled(false);
                    mEditText.setBackgroundResource(mainBackGround);
                }

            } else {
                setErrorEnabled(false);
                mEditText.setBackgroundResource(mainBackGround);
            }

            int p = 15;
            mEditText.setPadding(p, p, p, p);
        }

    }

    public void hideSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);

        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        mEditText.clearFocus();

    }

    public String getString() {
        return getText().toString().trim();
    }

    public Editable getText() {
        return mEditText.getText();
    }

    private boolean isEmail(String str) {
        return EMAIL_PATTERN.matcher(str).matches();
    }

    private boolean isPassword(String str) {
        return PASSWORD_PATTERN.matcher(str).matches();
    }

    private boolean isPersonName(String str) {

        return PERSON_NAME_PATTERN.matcher(str).matches();
    }

    private boolean isPersonName() {
        String str = getText().toString().trim();
        return isPersonName(str);
    }

    private boolean isEmail() {
        String str = getText().toString().trim();
        return isEmail(str);
    }

    private boolean isPassword() {
        String str = getText().toString().trim();
        return isPassword(str);
    }


    public boolean isEmpty() {
        boolean em = isEmptySilent();
        if (em) {
            setInternalError(STRING_EMPTY_FIELD);
        }
        return em;
    }

    public boolean isEmptySilent() {
        return TextUtil.isEmpty(getString());
    }

    /*Confirm to Field setted in xml*/
    public boolean confirmToField(boolean silent) {

        MaterialEditText toCompare = null;
        View view = null;
        try {
            view = ViewGroup.class.cast(getParent()).findViewWithTag("fieldToConfirm");
        } catch (ClassCastException e) {
            e.printStackTrace();
        }

        boolean compare = false;

        if (view == null)
            return false;

        if (view instanceof MaterialEditText) {

            toCompare = (MaterialEditText) view;

            if (TextUtil.trimEditText(this).isEmpty() || TextUtil.trimEditText(toCompare).isEmpty()) {
                return false;
            }

            compare = TextUtil.trimEditText(this).equals(TextUtil.trimEditText(toCompare));

            if (!compare) {
                if (!silent)
                    setInternalError(STRING_PASSWORD_IS_NOT_EQUAL);
//                toCompare.setInternalError(STRING_PASSWORD_IS_NOT_EQUAL);
            } else {
                setInternalError("");
                toCompare.setInternalError("");
            }
        } else if (view instanceof EditText) {
            return confirmToField((EditText) view);
        }

        return compare;
    }

    private boolean confirmToField(EditText text) {
        if (TextUtil.trimEditText(this).isEmpty() || TextUtil.trimEditText(text).isEmpty()) {
            return false;
        }

        boolean compare = TextUtil.trimEditText(this).equals(TextUtil.trimEditText(text));

        if (!compare) {
            setInternalError(STRING_PASSWORD_IS_NOT_EQUAL);
//            toCompare.setInternalError(STRING_PASSWORD_IS_NOT_EQUAL);
        } else {
            setInternalError("");

        }

        return compare;
    }

    public void setText(String s) {
        mEditText.setText(s);
    }

    public void setInternalHint(CharSequence s) {
        mEditText.setHint(s);
    }

    @Override
    public void setEnabled(boolean enabled) {
        mEditText.setEnabled(enabled);
    }

    public void clear() {
        setText("");
    }

    public int length() {
        return mEditText.length();
    }

    public String getErrorText() {
        return error.getText().toString();
    }
}
