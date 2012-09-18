package hikst.frontend.client;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SaveSimulatorObjectCallback implements AsyncCallback<Integer>
{
	private MyDockLayoutPanel panel;
	
	public SaveSimulatorObjectCallback(MyDockLayoutPanel panel)
	{
		this.panel = panel;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		
		Window.alert(caught.getMessage());
		
	}

	@Override
	public void onSuccess(Integer result) {
		
		panel.objectAdded = true;
		panel.simManager.setId(result);
		panel.statusField.setText("Object saved to database");
	}

}
