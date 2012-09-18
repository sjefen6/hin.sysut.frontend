package hikst.frontend.server;

public class User {

	private String username;
	private String firstname;
	private String lastname;
	private String email;
	private String salt;
	private String access_id;
	
	public String getUsername() {
		return username;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public String getEmail() {
		return email;
	}

	public String getSalt() {
		return salt;
	}

	public String getAccess_id() {
		return access_id;
	}

	public User(String username, String firstname, String lastname, String email,String salt, String access_id)
	{
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.salt = salt;
		this.access_id = access_id;
	}
}
