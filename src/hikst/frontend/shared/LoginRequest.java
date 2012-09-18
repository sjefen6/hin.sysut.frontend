package hikst.frontend.shared;

import java.io.Serializable;


@SuppressWarnings("serial")
public class LoginRequest implements Serializable
{
	private String username;
	private String password;
	
	public LoginRequest(){
		
	}
	
	public String getUsername()
	{
		return username;
	}
	
	public String getPassword()
	{
		return password;
	}
	
	public LoginRequest(String username, String password)
	{
		this.username = username;
		this.password = password;
	}
}
