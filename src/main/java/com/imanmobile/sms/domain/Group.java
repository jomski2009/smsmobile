package com.imanmobile.sms.domain;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jome on 2014/02/28.
 */
@Entity(value = "groups")
public class Group implements Serializable {
    @Id
    private ObjectId groupid;
    private String name;
    private String description;
    private String username;
    private List<Recipient> recipients = new ArrayList<>();

    public ObjectId getGroupid() {
        return groupid;
    }

    public void setGroupid(ObjectId groupid) {
        this.groupid = groupid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
    }
}
