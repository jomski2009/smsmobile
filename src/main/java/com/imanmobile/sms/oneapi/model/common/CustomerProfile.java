package com.imanmobile.sms.oneapi.model.common;

public class CustomerProfile {
	private int id;

    private String key;
    private String accountKey;
	private String username;
	private String forename;
	private String surname;
	private String street;
	private String city;
	private String zipCode;
	private String telephone;
	private String gsm;
	private String fax;
	private String email;
	private String skype;
    private Integer countryId;
    private Integer timezoneId;
    private boolean enabled;

    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
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

    @Override
	public String toString() {
		return "CustomerProfile {id=" + id + ", username=" + username
				+ ", forename=" + forename + ", surname=" + surname
				+ ", street=" + street + ", city=" + city + ", zipCode="
				+ zipCode + ", telephone=" + telephone + ", gsm=" + gsm
				+ ", fax=" + fax + ", email=" + email +
				", skype=" + skype + ", countryId=" + countryId
				+ ", timezoneId=" + timezoneId + ", enabled=" + enabled + "}";
	}
}
