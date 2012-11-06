package hikst.frontend.client.pages;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.Graph;
import hikst.frontend.client.Simulation;
import hikst.frontend.client.SplineGraph;
import hikst.frontend.client.callback.DescriptionsCallback;
import hikst.frontend.shared.Description;
import hikst.frontend.shared.SimulationTicket;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class SimulatonResult extends Composite {

	Simulation simulation;
	Description description;
	SplineGraph spline;
	@UiField FlowPanel centerPanel;
	public SimulationTicket ticket;
	boolean done = false;
	private DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);
	
	private static SimulatonResultUiBinder uiBinder = GWT
			.create(SimulatonResultUiBinder.class);

	interface SimulatonResultUiBinder extends UiBinder<Widget, SimulatonResult> {
	}

	public SimulatonResult() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	

	public SimulatonResult(String name) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button backButton;
	private AsyncCallback<Integer> callback ;


	@UiHandler("backButton")
	void onClick(ClickEvent e) {

	}
	
	public void setData(Description description){
		simulation = new Simulation(description);
		centerPanel.clear();
		Graph powerGraph = simulation.getEffectGraph();
		centerPanel.add(powerGraph);
		powerGraph.update();
		done = true;
	}
	
	public void setSimulationTicket(SimulationTicket ticket){
		this.ticket = ticket;
	}
	
	private void doStuff(){
		if (done == true){
			databaseService.getSimulation(ticket.getDescriptionID(), new DescriptionsCallback(this));
		}
		else {
			//TODO: 
		}
	}
}
