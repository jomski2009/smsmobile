package com.imanmobile.sms.oneapi.listener;

import java.util.EventListener;

import com.imanmobile.sms.oneapi.model.common.InboundSMSMessageList;

public interface InboundMessageListener extends EventListener {
	public void onMessageRetrieved(InboundSMSMessageList inboundSMSMessageList, Throwable error);
}
