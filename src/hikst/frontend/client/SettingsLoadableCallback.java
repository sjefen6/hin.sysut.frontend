package hikst.frontend.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SettingsLoadableCallback implements AsyncCallback<Boolean>{
	
	public SettingsLoadableCallback(){

	}
	
	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		Window.alert("Exception at server :"+caught.getLocalizedMessage());
	}

	@Override
	public void onSuccess(Boolean result) {
		Start.go(result);
	}
}
