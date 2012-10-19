package hikst.frontend.client.callback;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SaveObjectCallback implements AsyncCallback<Boolean> {

	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.getMessage());
		
	}

	@Override
	public void onSuccess(Boolean result) {
	
		if(result){
			Window.alert("Amazing. The object was successfully stored in the database");
		}
		else{
			Window.alert("The object was not stored into the database");
		}
		
	}

}
