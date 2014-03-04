package com.imanmobile.sms.domain;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Created by jome on 2014/02/28.
 */

@Embedded
public class Account {
    private String name;
    private String accountid;
    private double smsvalue;
    private double balance;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountid() {
        return accountid;
    }

    public void setAccountid(String accountid) {
        this.accountid = accountid;
    }

    public double getSmsvalue() {
        return smsvalue;
    }

    public void setSmsvalue(double smsvalue) {
        this.smsvalue = smsvalue;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
