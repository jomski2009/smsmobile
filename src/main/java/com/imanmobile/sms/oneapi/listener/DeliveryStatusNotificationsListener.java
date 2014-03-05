package com.imanmobile.sms.oneapi.listener;

import java.util.EventListener;

import com.imanmobile.sms.oneapi.model.DeliveryInfoNotification;

public interface DeliveryStatusNotificationsListener extends EventListener {
	public void onDeliveryStatusNotificationReceived(DeliveryInfoNotification deliveryInfoNotification);
}
