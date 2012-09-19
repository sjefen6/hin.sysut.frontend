package hikst.frontend.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SetupCallback implements AsyncCallback<Boolean>  {

	@Override
	public void onFailure(Throwable caught) {
		
		
		Window.alert("unable to contact server");
	}

	@Override
	public void onSuccess(Boolean result) {
		
		if(result == true)
		{
			Window.alert("db successfully set up");
		}
		else
		{
			Window.alert("db setup values is invalid");
		}
		
	}

}
