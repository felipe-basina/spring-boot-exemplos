package br.com.becommerce.security.api.model;

public class TokenCredential {

	private String token;

	private String user;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public TokenCredential(String token, String user) {
		super();
		this.token = token;
		this.user = user;
	}

}
