package com.imanmobile.sms.oneapi.listener;

import java.util.EventListener;

import com.imanmobile.sms.oneapi.model.common.InboundSMSMessageList;

public interface InboundMessageNotificationsListener extends EventListener {
	public void onMessageReceived(InboundSMSMessageList inboundSMSMessageList);
}
