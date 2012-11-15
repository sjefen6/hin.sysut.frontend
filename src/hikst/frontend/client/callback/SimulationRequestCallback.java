package hikst.frontend.client.callback;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;


public class SimulationRequestCallback implements AsyncCallback<Integer> {
	
	public SimulationRequestCallback()
	{
		
	}
	
	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(Integer Simulation_ID) {
		Window.alert("lagret!" + Simulation_ID );
	}
}
