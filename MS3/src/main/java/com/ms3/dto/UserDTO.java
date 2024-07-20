package com.ms3.dto;

import java.util.Date;

import org.apache.ibatis.type.Alias;


@Alias("user")
public class UserDTO {
	private String id;
	private int grantNo;
	private String password;
	private String email;
	private String name;
	private String nickname;
	private String profile;
	private String passwardResetToken;
	private Date tokenExpiryTime;
	
	public UserDTO() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getGrantNo() {
		return grantNo;
	}

	public void setGrantNo(int grantNo) {
		this.grantNo = grantNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getPasswardResetToken() {
		return passwardResetToken;
	}

	public void setPasswardResetToken(String passwardResetToken) {
		this.passwardResetToken = passwardResetToken;
	}

	public Date getTokenExpiryTime() {
		return tokenExpiryTime;
	}

	public void setTokenExpiryTime(Date tokenExpiryTime) {
		this.tokenExpiryTime = tokenExpiryTime;
	}

	@Override
	public String toString() {
		return "UserDTO [id=" + id + ", grantNo=" + grantNo + ", password=" + password + ", email=" + email + ", name="
				+ name + ", nickname=" + nickname + ", profile=" + profile + ", passwardResetToken="
				+ passwardResetToken + ", tokenExpiryTime=" + tokenExpiryTime + "]";
	}



	
	
	
	
}