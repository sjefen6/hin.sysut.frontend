package hikst.frontend.client.callback;

import hikst.frontend.client.pages.NewSimulation;
import hikst.frontend.shared.HikstObjectTree;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

//not finished
public class TreeCallback implements AsyncCallback<HikstObjectTree>
{
	private NewSimulation objectMenu;
	
	public TreeCallback(NewSimulation objectMenu)
	{
		this.objectMenu = objectMenu;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		Window.alert("Unable to contact server :"+caught.getMessage());
		
	}

	@Override
	public void onSuccess(HikstObjectTree result) {
	
		this.objectMenu.tree.clear();
		this.objectMenu.tree.addItem(result.getTree());
		
		
	}
	
}
