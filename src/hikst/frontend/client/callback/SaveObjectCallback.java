package hikst.frontend.client.callback;

import hikst.frontend.shared.HikstObject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SaveObjectCallback implements AsyncCallback<Integer> {

	private HikstObject simObject;
	
	public SaveObjectCallback(HikstObject simObject){
		this.simObject = simObject;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.getMessage());
		
	}

	@Override
	public void onSuccess(Integer result) {
	
		if(result != -1){
			this.simObject.setID(result);
			Window.alert("Amazing. The object was successfully stored in the database");
		}
		else{
			Window.alert("The object was not stored into the database");
		}
		
	}

}
