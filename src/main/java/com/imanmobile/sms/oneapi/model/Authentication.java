package com.imanmobile.sms.oneapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

@JsonSerialize(include = Inclusion.NON_NULL)
public class Authentication {
	
	public enum AuthType {
		BASIC, IBSSO, OAUTH;
	};
	
	//Default Authentication Type
	private AuthType type = AuthType.BASIC;	
	//BASIC Authentication parameters
	private String username = "";
	private String password = "";
	//OAUTH Authentication parameter 
	private String accessToken = "";
	//IBSSO Authentication parameter
	private String ibssoToken = "";
	
	public Authentication() {  
		super();
	}
	
	/**
	 * Initialize 'BASIC' Authentication (to use 'IBSSO' Authentication you need to call 'CustomerProfileClient.login()' method after client initialization)
	 * @param username
	 * @param password
	 */
	public Authentication(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Initialize 'OAUTH' Authentication
	 * @param accessToken
	 */
	public Authentication(String accessToken) {
		this.accessToken = accessToken;
		this.type = AuthType.OAUTH;
	}

	/**
	 * Get Authentication type 
	 * @return AuthType - (AuthType.OAUTH, AuthType.IBSSO)
	 */
	public AuthType getType() {
		return type;
	}
	/**
	 * Set Authentication type 
	 * @param type - (AuthType.OAUTH, AuthType.IBSSO)
	 */
	public void setType(AuthType type) {
		this.type = type;
	}

	/**
	 * Get 'Basic' Authentication user name 
	 * @return String
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Set Authentication user name
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Get Authentication password
	 * @return String
	 */
	public String getPassword() {
		return password;
	}
	
	/**
	 *  Set Authentication password
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Get 'IBSSO' Authentication Access Token
	 * @return String
	 */
	public String getAccessToken() {
		return accessToken;
	}
	
	/**
	 * Set 'IBSSO' Authentication Access Token
	 * @param accessToken
	 */
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * Get 'IBBSSO' Token
	 * @return ibssoToken
	 */
	@JsonIgnore
	public String getIbssoToken() {
		return ibssoToken;
	}

	/**
	 * Set 'IBBSSO' Token
	 * @param ibssoToken
	 */
	@JsonIgnore
	public void setIbssoToken(String ibssoToken) {
		this.ibssoToken = ibssoToken;
	}

    @Override
    public String toString() {
        return "Authentication{" +
                "type=" + type +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", ibssoToken='" + ibssoToken + '\'' +
                '}';
    }
}