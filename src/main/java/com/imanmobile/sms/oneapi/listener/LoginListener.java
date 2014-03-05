package com.imanmobile.sms.oneapi.listener;

import java.util.EventListener;

import com.imanmobile.sms.oneapi.model.common.LoginResponse;

public interface LoginListener extends EventListener {
    public void onLogin(LoginResponse response);
}
