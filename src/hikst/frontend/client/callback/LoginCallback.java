package hikst.frontend.client.callback;

import hikst.frontend.client.pages.Login;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LoginCallback implements AsyncCallback<Boolean> 
{
	Login login;

	public LoginCallback(Login login)
	{
		this.login = login;
	}

	@Override
	public void onFailure(Throwable caught) {

		Window.alert("status code 403");

	}

	@Override
	public void onSuccess(Boolean access_granted) {


		if(access_granted)
		{
			//Window.alert("Access granted");
			System.out.println("LoginCallback / access granted");
			login.GoToMainPage();
		}
		else
		{
			Window.alert("Access not granted");
		}
	}

}