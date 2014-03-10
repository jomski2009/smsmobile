package com.imanmobile.sms.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jome on 2014/02/28.
 */
@Entity(value = "groups")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Group implements Serializable {
    @Id
    private ObjectId groupid;

    @NotNull
    private String name;
    private String description;
    private List<Recipient> recipients = new ArrayList<>();
    private long creationdate;

    private String accountKey;
    private String groupidString;

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

    public List<Recipient> getRecipients() {
        return recipients;
    }

    public void setRecipients(List<Recipient> recipients) {
        this.recipients = recipients;
    }

    public String getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }

    public long getCreationdate() {
        return creationdate;
    }

    public void setCreationdate(long creationdate) {
        this.creationdate = creationdate;
    }

    public String getGroupidString() {
        return groupid.toString();
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupid=" + groupid +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", recipients=" + recipients +
                ", creationdate=" + creationdate +
                ", accountKey='" + accountKey + '\'' +
                '}';
    }
}
