package hikst.frontend.client.callback;

import hikst.frontend.client.pages.MyDockLayoutPanel;
import hikst.frontend.shared.Description;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ListDescriptionsCallback implements AsyncCallback<List<Description>> {

	MyDockLayoutPanel panel;
	
	ListDescriptionsCallback(MyDockLayoutPanel panel){
		this.panel = panel;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		
		Window.alert(caught.getMessage());
		
	}

	@Override
	public void onSuccess(List<Description> result){
		
		//TODO:
		//add all the finished simulations....
		
		if(result.size() > 0)
		{
			try{
				panel.setData(result.get(0));
			}
			catch(Exception e){
				
			}
			
		}
		else
		{
			Window.alert("No finished simulations found in the database");
		}
		
	}

}
