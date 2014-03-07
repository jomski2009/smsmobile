package com.imanmobile.sms.oneapi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendMessageResultItem {

	private String messageStatus;
	private String messageId;
	private String senderAddress;
	private String destinationAddress;
    private double price;
    private String errorMessageId;

	public SendMessageResultItem() {
		super();
	}

	public String getMessageStatus() {
		return messageStatus;
	}

	public void setMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getSenderAddress() {
		return senderAddress;
	}

	public void setSenderAddress(String senderAddress) {
		this.senderAddress = senderAddress;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getErrorMessageId() {
        return errorMessageId;
    }

    public void setErrorMessageId(String errorMessageId) {
        this.errorMessageId = errorMessageId;
    }

    @Override
	public String toString() {
		return "SendMessageResultItem {messageStatus=" + messageStatus
				+ ", messageId=" + messageId + ", senderAddress="
				+ senderAddress + ", destinationAddress=" + destinationAddress
				+ "}";
	}
}
