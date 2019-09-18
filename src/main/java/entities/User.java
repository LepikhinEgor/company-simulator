package entities;

public class User {
	
	private long id;
	private String login;
	private String email;
	private String password;
	
	public User() {
	}
	
	public long getId() {
		return id;
	}
	
	public String getLogin() {
		return this.login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", email=" + email + ", password=" + password + "]";
	}
	
}
