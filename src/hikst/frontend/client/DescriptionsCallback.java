package hikst.frontend.client;

import hikst.frontend.shared.Description;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DescriptionsCallback implements AsyncCallback<Description>
{
	MyDockLayoutPanel panel;
	
	DescriptionsCallback(MyDockLayoutPanel panel)
	{
		this.panel = panel;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		
		Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(Description result) {
		
		panel.setData(result);
	}

}
