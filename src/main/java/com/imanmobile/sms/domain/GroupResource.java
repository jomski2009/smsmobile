package com.imanmobile.sms.domain;

/**
 * Created by jome on 2014/03/10.
 */
public class GroupResource {
    private String name;
    private String description;
    private String groupid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGroupid() {
        return groupid;
    }

    public void setGroupid(String groupid) {
        this.groupid = groupid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
