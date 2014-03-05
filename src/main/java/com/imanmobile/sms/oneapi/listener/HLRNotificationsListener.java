package com.imanmobile.sms.oneapi.listener;

import java.util.EventListener;

import com.imanmobile.sms.oneapi.model.RoamingNotification;

public interface HLRNotificationsListener extends EventListener {
	public void OnHLRReceived(RoamingNotification roamingNotification);
}
