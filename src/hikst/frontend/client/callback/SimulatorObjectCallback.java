package hikst.frontend.client.callback;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

import hikst.frontend.client.SimulationManagementObject;
import hikst.frontend.shared.*;

public class SimulatorObjectCallback implements AsyncCallback<SimulatorObject>
{
	SimulationManagementObject simulationManagementObject;
	
	public SimulatorObjectCallback(SimulationManagementObject simulationManagementObject)
	{
		this.simulationManagementObject = simulationManagementObject;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		
		Window.alert(caught.getMessage());
		
	}

	@Override
	public void onSuccess(SimulatorObject result) {
		
		this.simulationManagementObject.load(result);
	}

}