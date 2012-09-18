package hikst.frontend.shared;

import java.io.Serializable;


@SuppressWarnings("serial")
public class RegisterRequest implements Serializable
{
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	private String email;
	
	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
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

	public RegisterRequest(){}
	
	public RegisterRequest(String username,String password, String firstname,String lastname, String email)
	{
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
	}
}
