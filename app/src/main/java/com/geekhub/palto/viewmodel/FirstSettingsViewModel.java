package com.geekhub.palto.viewmodel;

import com.geekhub.palto.activity.FirstSettingsActivity;
import com.geekhub.palto.binding.BindableBoolean;
import com.geekhub.palto.binding.BindableString;
import com.geekhub.palto.useragent.UserAgent;
import com.vk.sdk.api.model.VKApiPhoto;

/**
 * Created by duke0808 on 29.01.16.
 */
public class FirstSettingsViewModel {
    public BindableString getUserId() {
        return userId;
    }

    public void setUserId(BindableString userId) {
        this.userId = userId;
    }

    public BindableString getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(BindableString imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BindableString getNickName() {
        return nickName;
    }

    public void setNickName(BindableString nickName) {
        this.nickName = nickName;
    }

    FirstSettingsActivity activity;
    BindableString userId = new BindableString();
    BindableString imageUrl = new BindableString();
    BindableString nickName = new BindableString();

    public FirstSettingsViewModel(FirstSettingsActivity activity) {
        this.activity = activity;
        userId.set(UserAgent.get(activity).getUserId());
    }
}
