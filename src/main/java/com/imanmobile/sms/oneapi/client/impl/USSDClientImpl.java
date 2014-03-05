package com.imanmobile.sms.oneapi.client.impl;

import com.imanmobile.sms.oneapi.client.USSDClient;
import com.imanmobile.sms.oneapi.config.Configuration;
import com.imanmobile.sms.oneapi.model.RequestData;
import com.imanmobile.sms.oneapi.model.USSDRequest;
import com.imanmobile.sms.oneapi.model.RequestData.Method;
import com.imanmobile.sms.oneapi.model.common.InboundSMSMessage;

public class USSDClientImpl extends OneAPIBaseClientImpl implements USSDClient {

	private static final String USSD_URL_BASE = "/ussd/outbound";
	
	public USSDClientImpl(Configuration configuration) {
		super(configuration);
	}

	/**
	 * Send an USSD over OneAPI to one mobile terminal '
	 * @param address
	 * @param message
	 * @return InboundSMSMessage
	 */
	@Override
	public InboundSMSMessage sendMessage(String address, String message) {
		RequestData requestData = new RequestData(USSD_URL_BASE, Method.POST);
		requestData.setFormParams(new USSDRequest(address, message));
		requestData.setContentType(URL_ENCODED_CONTENT_TYPE);
		return executeMethod(requestData, InboundSMSMessage.class);
	}

	/**
	 * Stop USSD session
	 * @param address
	 * @param message
	 */
	@Override
	public void stopSession(String address, String message) {
		RequestData requestData = new RequestData(USSD_URL_BASE, Method.POST);
		requestData.setFormParams(new USSDRequest(address, message, true));
		requestData.setContentType(URL_ENCODED_CONTENT_TYPE);
	    executeMethod(requestData);
	}
}
