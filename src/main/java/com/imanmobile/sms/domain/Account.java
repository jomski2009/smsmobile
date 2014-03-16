package com.imanmobile.sms.domain;

import com.imanmobile.sms.oneapi.model.common.AccountBalance;
import com.imanmobile.sms.oneapi.model.common.CustomerPricing;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.Date;

/**
 * Created by jome on 2014/02/28.
 */

@Entity(value = "accounts")
public class Account {

    private int id;
    @Id
    private String key;
    private String accountKey;
    private String parentKey;
    private String name;
    private Date dateCreated;
    private String additionalProperties;
    private String timeZoneId;
    private String currencyId;
    private String companyId;
    private String countryId;
    private String countryCode;
    private String street;
    private String city;
    private String zipCode;
    private String defaultSender;

    private AccountBalance accountBalance;

    private CustomerPricing customerPricing;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAccountKey() {
        return key;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(String additionalProperties) {
        this.additionalProperties = additionalProperties;
    }

    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }

    public String getCurrencyId() {
        return currencyId;
    }

    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getDefaultSender() {
        return defaultSender;
    }

    public void setDefaultSender(String defaultSender) {
        this.defaultSender = defaultSender;
    }

    public AccountBalance getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(AccountBalance accountBalance) {
        this.accountBalance = accountBalance;
    }

    public CustomerPricing getCustomerPricing() {
        return customerPricing;
    }

    public void setCustomerPricing(CustomerPricing customerPricing) {
        this.customerPricing = customerPricing;
    }

    @Override
    public String toString() {
        return "Account{" +
                "key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", currencyId='" + currencyId + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", defaultSender='" + defaultSender + '\'' +
                '}';
    }
}
