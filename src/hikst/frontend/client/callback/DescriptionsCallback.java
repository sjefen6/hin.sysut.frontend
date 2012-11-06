package hikst.frontend.client.callback;

import hikst.frontend.client.pages.MyDockLayoutPanel;
import hikst.frontend.client.pages.SimulatonResult;
import hikst.frontend.shared.Description;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class DescriptionsCallback implements AsyncCallback<Description>
{
	SimulatonResult panel;
	
	
	public DescriptionsCallback(SimulatonResult simulatonResult)
	{
		this.panel = simulatonResult;
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
