package com.geekhub.palto.util;

import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.EditText;


import com.geekhub.palto.customviews.MaterialEditText;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/**
 * Created by lietto on 05.11.14.
 */
public class TextUtil {

    //    public static String trimEditText(ValidationEditText text) {
//        return text.getText().toString().trim();
//    }
    public static String trimEditText(MaterialEditText text) {
        return text.getText().toString().trim();
    }

    public static String trimEditText(EditText text) {
        return text.getText().toString().trim();
    }

    /**
     * TODO: description!
     */
    public static String keepDigitsOnly(String text) {
        if (isEmpty(text))
            return "";

        StringBuilder sb = new StringBuilder();
        for (int idx = 0, count = text.length(); idx < count; ++idx) {
            char chr = text.charAt(idx);

            if (!Character.isDigit(chr))
                continue;

            sb.append(chr);
        }

        // done
        return sb.toString();
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * TODO: description!
     */
    public static int indexOfLastDigit(String text) {
        int position = 0;
        for (int idx = text.length() - 1; idx >= 0; --idx) {
            if (Character.isDigit(text.charAt(idx))) {
                position = idx + 1;
                break;
            }
        }

        // done
        return position;
    }


    public static boolean isEmpty(CharSequence str) {
        if (str != null) {
            str = str.toString().trim();
            if (str.length() == 0 || str.toString().equalsIgnoreCase("") || str.toString().equalsIgnoreCase("null"))
                return true;
            else
                return false;
        } else {
            return true;
        }

    }


    public static String getPathFromUri(Activity ctx, Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = ctx.managedQuery(uri, projection, null, null, null);
        ctx.startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

}
