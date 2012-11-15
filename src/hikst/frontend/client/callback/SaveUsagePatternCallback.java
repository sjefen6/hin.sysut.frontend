package hikst.frontend.client.callback;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SaveUsagePatternCallback implements AsyncCallback<Void> {

	@Override
	public void onFailure(Throwable caught) {
		Window.alert("Failed to save usage pattern");
		
	}

	@Override
	public void onSuccess(Void result) {
		Window.alert("Amazing! Usage pattern saved!");
	}

}
