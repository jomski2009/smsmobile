package com.imanmobile.sms.oneapi.client;

import com.imanmobile.sms.oneapi.model.common.InboundSMSMessage;

public interface USSDClient {

	/**
	 * Send an USSD over OneAPI to one mobile terminal 
	 * @param address
	 * @param message
	 * @return InboundSMSMessage
	 */
	InboundSMSMessage sendMessage(String address, String message);

	/**
	 * Stop USSD session
	 * @param address
	 * @param message
	 */
	void stopSession(String address, String message);

}
