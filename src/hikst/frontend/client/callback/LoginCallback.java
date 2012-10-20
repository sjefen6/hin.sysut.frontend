package hikst.frontend.client.callback;

import hikst.frontend.client.pages.Login;
import hikst.frontend.client.pages.MainPage;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class LoginCallback implements AsyncCallback<Boolean> 
{
	Login login;
	

	public LoginCallback(Login login)
	{
		this.login = login;
	}

	@Override
	public void onFailure(Throwable caught) {

		RootLayoutPanel.get().add(new Login());
		login = new Login();
		Window.alert("status code 403"+ "Something wrong happend on the serverside, try again later");

	}

	@Override
	public void onSuccess(Boolean access_granted) {


		if(access_granted)
		{
			Window.alert("Welkommen hikst");
			System.out.println("LoginCallback / access granted");
			login.GoToMainPage();
		}
		else
		{
			RootLayoutPanel.get().add(new Login());
			login = new Login();
			Window.alert("Feil brukernavn eller passord");
		}
	}

}