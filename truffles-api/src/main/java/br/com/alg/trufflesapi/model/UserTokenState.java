package br.com.alg.trufflesapi.model;

public class UserTokenState {

	private String access_token;
    private Long expires_in;
    
    private Account account;

    public UserTokenState() {
        this.access_token = null;
        this.expires_in = null;
    }

    public UserTokenState(Account account, String access_token, long expires_in) {
        this.access_token = access_token;
        this.expires_in = expires_in;
        this.account = account;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public Long getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(Long expires_in) {
        this.expires_in = expires_in;
    }

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
  
}
