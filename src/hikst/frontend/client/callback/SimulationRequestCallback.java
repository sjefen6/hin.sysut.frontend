package hikst.frontend.client.callback;

import hikst.frontend.client.pages.MyDockLayoutPanel;
import hikst.frontend.client.pages.NewSimulation;
import hikst.frontend.shared.SimulationTicket;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SimulationRequestCallback implements AsyncCallback<SimulationTicket> {

	NewSimulation panel;
	
	public SimulationRequestCallback(NewSimulation newSimulation)
	{
		this.panel = newSimulation;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		// TODO Auto-generated method stub
		Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(SimulationTicket result) {
		
		panel.setSimulationTicket(result);

	}

}
