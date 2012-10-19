package hikst.frontend.client.callback;

import hikst.frontend.client.pages.NewSimulation;
import hikst.frontend.shared.SimObjectTree;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class UpdateSimObjectTreeObjectMenuCallback implements AsyncCallback<SimObjectTree>{

	private NewSimulation objectMenu;
	
	public UpdateSimObjectTreeObjectMenuCallback(NewSimulation objectMenu){
		this.objectMenu = objectMenu;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		
		Window.alert("Server error: " + caught.getMessage());
		
	}

	@Override
	public void onSuccess(SimObjectTree result) {
		
		this.objectMenu.setSimObject(result);
		
	}

	
	
}
