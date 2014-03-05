package com.imanmobile.sms.oneapi.listener;

import java.util.EventListener;
import com.imanmobile.sms.oneapi.model.DeliveryReportList;

public interface DeliveryReportListener extends EventListener {
	public void onDeliveryReportReceived(DeliveryReportList deliveryReportList, Throwable error);
}
