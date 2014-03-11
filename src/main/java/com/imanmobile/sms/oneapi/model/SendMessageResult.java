package com.imanmobile.sms.oneapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.imanmobile.sms.oneapi.model.common.ResourceReference;
import org.mongodb.morphia.annotations.Entity;

import java.util.Arrays;

@Entity("smsresults")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendMessageResult {
     private String accountKey;
	private String clientCorrelator;
	private SendMessageResultItem[] sendMessageResults;
	private ResourceReference resourceReference;
	
	public SendMessageResult() {
		super();
	}

	public String getClientCorrelator() {
		return clientCorrelator;
	}

	public void setClientCorrelator(String clientCorrelator) {
		this.clientCorrelator = clientCorrelator;
	}

	public SendMessageResultItem[] getSendMessageResults() {
		return sendMessageResults;
	}

	public void setSendMessageResults(SendMessageResultItem[] sendMessageResults) {
		this.sendMessageResults = sendMessageResults;
	}

	public ResourceReference getResourceReference() {
		return resourceReference;
	}

	public void setResourceReference(ResourceReference resourceReference) {
		this.resourceReference = resourceReference;
	}

    public String getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }

    @Override
	public String toString() {
		return "SendMessageResult {clientCorrelator=" + clientCorrelator
				+ ", sendMessageResults=" + Arrays.toString(sendMessageResults)
				+ ", resourceReference=" + resourceReference + "}";
	}	
}
