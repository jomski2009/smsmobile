package com.imanmobile.sms.services.impl;

import com.imanmobile.sms.domain.*;
import com.imanmobile.sms.oneapi.client.impl.SMSClient;
import com.imanmobile.sms.oneapi.config.Configuration;
import com.imanmobile.sms.oneapi.model.SMSRequest;
import com.imanmobile.sms.oneapi.model.SendMessageResult;
import com.imanmobile.sms.oneapi.model.SendMessageResultItem;
import com.imanmobile.sms.oneapi.model.common.DeliveryInfoList;
import com.imanmobile.sms.oneapi.model.common.ResourceReference;
import com.imanmobile.sms.services.AccountsService;
import com.imanmobile.sms.services.SmsService;
import com.imanmobile.sms.services.UserService;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by jome on 2014/03/04.
 */

@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    Datastore ds;

    @Autowired
    UserService userService;

    @Autowired
    AccountsService accountsService;

    @Autowired
    Configuration configuration;
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Map<String, Object> sendSms(String message) {
        Map<String, Object> outcome = new HashMap<>();
        Configuration configuration = new Configuration("allangray", "wordpass15");
        SMSClient smsClient = new SMSClient(configuration);

        SMSRequest request = new SMSRequest("MyAllanGray", message, "tel:+27837930939");
        request.setClientCorrelator("secondjavamessage");
//        String[] address = new String[]{"tel:+27837930939"};
//        request.setAddress(address);
        SendMessageResult sendMessageResult = smsClient.getSMSMessagingClient().sendSMS(request);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        DeliveryInfoList deliveryInfoList = smsClient.getSMSMessagingClient().queryDeliveryStatus("MyAllanGray", sendMessageResult.getClientCorrelator());
        String deliveryStatus = deliveryInfoList.getDeliveryInfo().get(0).toString();

        System.out.println(deliveryStatus);
        outcome.put("msgresult", sendMessageResult);
        outcome.put("status", deliveryStatus);
        return outcome;

    }

    @Override
    public SendMessageResult sendQuickSms(String message, String[] recipients) {
        int arLength = recipients.length;
        String[] reclist = new String[arLength];


        for (int i = 0; i < arLength; i++) {
            reclist[i] = recipients[i].trim();
        }
        for (String r : recipients) {
            logger.info("Recipients: {}", r);
        }


        SMSClient client = new SMSClient(configuration);
        String senderId = client.getCustomerProfileClient().getCustomerAccount().getDefaultSender();
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findByUsername(username);
        String accountKey = user.getAccountKey();

        SMSRequest smsRequest = new SMSRequest(senderId, message, reclist);
        //SMSRequest request = new SMSRequest("senderAddress","message to be sent","clientcorrelator","notifyurl","sendername","callbackdata", reclist);
        String messageId = "messageid_" + new Date().getTime();
        SMSRequest request = new SMSRequest(senderId, message, messageId, null, senderId, null, reclist);
        SendMessageResult messageResult = client.getSMSMessagingClient().sendSMS(request);
        logger.info(request.toString());


        //We should update the balance here, right?
        messageResult.setAccountKey(accountKey);
        Account account = accountsService.getAccountForKey(accountKey);

        for (SendMessageResultItem item : messageResult.getSendMessageResults()) {
            item.setPrice(account.getCustomerPricing().getPrices().get(0).getPrice());
        }

        ds.save(messageResult);
        ds.save(request);

        double balance = client.getCustomerProfileClient().getAccountBalance().getBalance();
        logger.info("New balance: {}", balance);

        UpdateOperations<Account> updateOperations = ds.createUpdateOperations(Account.class).set("accountBalance.balance", balance);
        UpdateResults<Account> updateResults = ds.update(ds.createQuery(Account.class).field("_id").equal(accountKey), updateOperations);


        return messageResult;
    }

    @Override
    public SendMessageResult sendBulkSms(BulkMessageDTO bulkMessage) {
        logger.info("processing bulk messaging");
        SMSClient client = new SMSClient(configuration);
        String groupId = bulkMessage.getGroupid();
        String text = bulkMessage.getBulkmessagetext();
        Group group = ds.createQuery(Group.class).field("groupid").equal(new ObjectId(groupId)).get();
        String senderId = client.getCustomerProfileClient().getCustomerAccount().getDefaultSender();
        String accountKey = client.getCustomerProfileClient().getCustomerAccount().getKey();
        Account account = accountsService.getAccountForKey(accountKey);

        String messageId = "messageid_" + new Date().getTime();
        String encodedSenderid = senderId.replaceAll(" ", "+");
        String resourceUrl = "http://oneapi.infobip.com/1/smsmessaging/outbound/" + encodedSenderid + "/requests/" + messageId + "/deliveryInfos";
        ResourceReference ref = new ResourceReference();
        ref.setResourceURL(resourceUrl);
        SendMessageResult fsmr = new SendMessageResult();
        fsmr.setAccountKey(accountKey);
        fsmr.setClientCorrelator(messageId);
        fsmr.setResourceReference(ref);


        for (Recipient recipient : group.getRecipients()) {
            String processedMessage = replaceTokens(text, recipient);
            logger.info("Processed message is : {}", processedMessage);
            String[] recList = new String[1];
            recList[0] = recipient.getCellnumber();

            SMSRequest request = new SMSRequest(senderId, processedMessage, messageId, null, senderId, null, recList);
            //Looks like we can use the infobip api to send this?
            //SMSRequest request = new SMSRequest(senderId, processedMessage, recipient.getCellnumber());
            ds.save(request);
            SendMessageResult messageResult = client.getSMSMessagingClient().sendSMS(request);
//            messageResult.getSendMessageResults().get(0).setPrice(account.getCustomerPricing().getPrices().get(0).getPrice());
//            messageResult.setAccountKey(accountKey);
//            ds.save(messageResult);

            SendMessageResultItem item = messageResult.getSendMessageResults().get(0);
            item.setPrice(account.getCustomerPricing().getPrices().get(0).getPrice());
            fsmr.getSendMessageResults().add(item);


        }
        ds.save(fsmr);

        double balance = client.getCustomerProfileClient().getAccountBalance().getBalance();
        logger.info("New balance: {}", balance);

        UpdateOperations<Account> updateOperations = ds.createUpdateOperations(Account.class).set("accountBalance.balance", balance);
        UpdateResults<Account> updateResults = ds.update(ds.createQuery(Account.class).field("_id").equal(accountKey), updateOperations);

        return fsmr;

    }

    public static String replaceTokens(String text, Recipient recipient) {
        Pattern pattern = Pattern.compile("\\[(.+?)\\]");
        Matcher matcher = pattern.matcher(text);

        HashMap<String, String> replacements = new HashMap<>();
        replacements.put("Firstname", recipient.getFirstname());
        replacements.put("Lastname", recipient.getLastname());
        replacements.put("Data1", recipient.getData1());
        replacements.put("Data2", recipient.getData2());
        replacements.put("Data3", recipient.getData3());
        replacements.put("Data4", recipient.getData4());

        StringBuilder builder = new StringBuilder();

        int i = 0;
        while (matcher.find()) {
            String replacement = replacements.get(matcher.group(1));
            builder.append(text.substring(i, matcher.start()));
            if (replacement == null)
                builder.append(matcher.group(0));
            else
                builder.append(replacement);
            i = matcher.end();
        }
        builder.append(text.substring(i, text.length()));
        return builder.toString();
    }

}
