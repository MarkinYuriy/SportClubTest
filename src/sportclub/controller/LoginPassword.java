package sportclub.controller;

public class LoginPassword {
	String login;
	
    public LoginPassword() {
			}

	public LoginPassword(String login, String password, String subprofile) {
		this.login = login;
		this.password = password;
		this.subprofile = subprofile;
	}

	public LoginPassword(String login, String password) {
		this.login = login;
		this.password = password;
	}

	String password;
    String subprofile;
    
    
    public String getSubprofile() {
		return subprofile;
	}

	public void setSubprofile(String subprofile) {
		this.subprofile = subprofile;
	}

	public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public String toString() {
		return "LoginPassword [login=" + login + ", password=" + password + ", subprofile=" + subprofile + "]";
	}
}
