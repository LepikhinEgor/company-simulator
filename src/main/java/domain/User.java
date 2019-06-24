package domain;

public class User {
	
	//temporary 
	private static int lastId = 0;
	
	private long id;
	private String nickname;
	private String password;
	
	public User(String nickname, String password) {
		this.nickname = nickname;
		this.password = password;
		this.id = ++lastId;
	}
	
	public long getId() {
		return id;
	}
	
	public String getNickname() {
		return this.nickname;
	}
	
}