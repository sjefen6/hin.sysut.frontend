package hikst.frontend.client.callback;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class StoreObjectCallback implements AsyncCallback<Integer>
{

	@Override
	public void onFailure(Throwable caught) {

		Window.alert("Unable to contact server :"+ caught.toString());

	}

	@Override
	public void onSuccess(Integer result) {

		if(result != 0)
		{
			Window.alert("Object successfully stored in database");
		}
		else
		{
			Window.alert("Not able to store in database");
		}

	}

}