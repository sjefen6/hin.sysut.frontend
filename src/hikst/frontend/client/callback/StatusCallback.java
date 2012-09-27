package hikst.frontend.client.callback;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.pages.MyDockLayoutPanel;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class StatusCallback implements AsyncCallback<Integer>
{
	MyDockLayoutPanel panel;
	DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);
	
	public StatusCallback(MyDockLayoutPanel panel)
	{
		this.panel = panel;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(Integer result) {
		
		if(result == 4)
		{
			panel.updateStatus("Ferdig");
			panel.simulationFinished = true;
		}
		else if(result == 3)
		{
			panel.updateStatus("I kø");
		}
		else if(result == 2)
		{
			panel.updateStatus("Ikke i kø");
		}
		else
		{
			panel.updateStatus("Invalid simulation status detected... : "+result);
		}
	}
}

