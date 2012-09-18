package hikst.frontend.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class UpdateCallback implements AsyncCallback<Boolean>{

	private boolean success = false;
	private boolean finished = false;
	
	public boolean isSuccess()
	{
		return success;
	}
	
	public boolean isFinished()
	{
		return finished;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		
		success = false;
		finished = true;
		Window.alert(caught.getMessage());
		
	}

	@Override
	public void onSuccess(Boolean result) {
		
		success = true;
		finished = true;
	}

}