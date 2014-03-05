package com.imanmobile.sms.domain;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jome on 2014/02/28.
 */

@Entity(value = "users")
public class User {
    @Id
    private String username;
    private String key;
    private String accountKey;
    private String forename;
    private String surname;
    private String street;
    private String city;
    private String zipCode;
    private String telephone;
    private String gsm;
    private String fax;
    private String email;
    private String msn;
    private String skype;
    private Integer countryId;
    private Integer timezoneId;
    private Integer primaryLanguageId;
    private Integer secondaryLanguageId;
    private boolean enabled;
    private int role;

    @Reference
    private Account account;

    @Reference(lazy = true)
    private List<Group> groups = new ArrayList<>();

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getAccountKey() {
        return accountKey;
    }

    public void setAccountKey(String accountKey) {
        this.accountKey = accountKey;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getGsm() {
        return gsm;
    }

    public void setGsm(String gsm) {
        this.gsm = gsm;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getTimezoneId() {
        return timezoneId;
    }

    public void setTimezoneId(Integer timezoneId) {
        this.timezoneId = timezoneId;
    }

    public Integer getPrimaryLanguageId() {
        return primaryLanguageId;
    }

    public void setPrimaryLanguageId(Integer primaryLanguageId) {
        this.primaryLanguageId = primaryLanguageId;
    }

    public Integer getSecondaryLanguageId() {
        return secondaryLanguageId;
    }

    public void setSecondaryLanguageId(Integer secondaryLanguageId) {
        this.secondaryLanguageId = secondaryLanguageId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", key='" + key + '\'' +
                ", accountKey='" + accountKey + '\'' +
                ", forename='" + forename + '\'' +
                ", surname='" + surname + '\'' +
                ", enabled=" + enabled +
                '}';
    }
}
