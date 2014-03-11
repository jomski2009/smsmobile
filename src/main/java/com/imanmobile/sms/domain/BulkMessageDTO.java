package com.imanmobile.sms.domain;

/**
 * Created by jome on 2014/03/11.
 */
public class BulkMessageDTO {
    private String messagedescription;
    private String groupid;
    private String bulkmessagetext;

    public String getMessagedescription() {
        return messagedescription;
    }

    public void setMessagedescription(String messagedescription) {
        this.messagedescription = messagedescription;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getBulkmessagetext() {
        return bulkmessagetext;
    }

    public void setBulkmessagetext(String bulkmessagetext) {
        this.bulkmessagetext = bulkmessagetext;
    }

    @Override
    public String toString() {
        return "BulkMessageDTO{" +
                "messagedescription='" + messagedescription + '\'' +
                ", groupid='" + groupid + '\'' +
                ", bulkmessagetext='" + bulkmessagetext + '\'' +
                '}';
    }
}
