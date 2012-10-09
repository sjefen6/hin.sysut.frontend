package hikst.frontend.client.callback;

import hikst.frontend.client.pages.ObjectMenu;
import hikst.frontend.shared.SimObjectTree;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

//not finished
public class SimObjectTreeCallback implements AsyncCallback<SimObjectTree>
{
	private ObjectMenu objectMenu;
	
	public SimObjectTreeCallback(ObjectMenu objectMenu)
	{
		this.objectMenu = objectMenu;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		Window.alert("Unable to contact server :"+caught.getMessage());
		
	}

	@Override
	public void onSuccess(SimObjectTree result) {
	
		
		
	}
	
}
