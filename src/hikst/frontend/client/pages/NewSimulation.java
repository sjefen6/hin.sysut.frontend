package hikst.frontend.client.pages;

import java.util.Date;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.Graph;
import hikst.frontend.client.Simulation;
import hikst.frontend.client.SplineGraf;
import hikst.frontend.client.callback.DescriptionsCallback;
import hikst.frontend.client.callback.SimulationRequestCallback;
import hikst.frontend.client.callback.TreeCallback;
import hikst.frontend.shared.Description;
import hikst.frontend.shared.HikstObject;
import hikst.frontend.shared.SimulationRequest;
import hikst.frontend.shared.SimulationTicket;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.FlowPanel;

public class NewSimulation extends HikstComposite {

	Login login;
	Composite panel;
	MainPage panelBack;
	private EmailAdmin mailPanel;
	private static NewSimulationUiBinder uiBinder = GWT
			.create(NewSimulationUiBinder.class);
	@UiField
	Button back;
	@UiField
	Button addObject;
	@UiField
	DateBox fromDate;
	@UiField
	DateBox toDate;
	@UiField
	TextBox intervall;
	@UiField
	Button buttonShowSpline;
	@UiField
	FlowPanel eastPanel;
	@UiField
	FlowPanel centerPanel;
	public @UiField
	Tree tree;
	private TreeCallback treeCallback;

	private boolean updated  = false;
	private long interval;
	private int objectCounter = 0;
	Simulation simulation;
	
	DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);
	Date toDate1, fromDate1;
	public SimulationTicket ticket;
	public boolean objectAdded = false;
	public boolean simulationStarted = false;
	public boolean simulationFinished = false;
	
	public int watt = 200;
	public int volt = 220;
	public String name = "test";
	public int intervaal = 10;
	
	
	
	
	interface NewSimulationUiBinder extends UiBinder<Widget, NewSimulation> {
	}

	public NewSimulation() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	private void updateTree(int id) {

		databaseService.loadObject(id, treeCallback);
	}

	public NewSimulation(HikstComposite parent, HikstObject simObject) {
		this();
		fromDate.setValue(((NewSimulation) parent).fromDate.getValue());
		toDate.setValue(((NewSimulation) parent).toDate.getValue());
		intervall.setValue(((NewSimulation) parent).intervall.getValue());

		treeCallback = new TreeCallback(this);

		updateTree(simObject.getID());
		
	}

	@UiHandler("addObject")
	void onAddObjectClick(ClickEvent event) {
		panel = new ViewObjects(this.parent);
		RootLayoutPanel.get().add(panel);
	}

	@UiHandler("back")
	void onBackClick(ClickEvent event) {
		panelBack = new MainPage();
		RootLayoutPanel.get().add(panelBack);
	}

	@UiHandler("buttonShowSpline")
	void onButtonShowSplineClick(ClickEvent event) {
		fromDate1 = fromDate.getValue();
		toDate1 = toDate.getValue();
		//centerPanel.clear();
		//centerPanel.add(SplineGraf.createChart());
		//System.out.println("Should show spline!!!");
	
		if (updated == true){
			if (this.objectAdded){
				ticket = new SimulationTicket(-1, false, 0);
				databaseService.requestSimulation(
						new SimulationRequest(
								objectCounter, //simManager.getId(),
								interval,
								fromDate1.getTime(),
								toDate1.getTime()
								),
								new SimulationRequestCallback(this));
				simulationStarted = true;
				
				
				
			}
		}
		while (simulationStarted){
			checkForResoult();
		}
	}
	

	@UiHandler("emailAdmin")
	void onEmailAdminClick(ClickEvent event) {
		mailPanel = new EmailAdmin();
		RootLayoutPanel.get().add(mailPanel);
	}
	
	public void setData(Description description){
		simulation = new Simulation(description);
		centerPanel.clear();
		Graph powerGraph = simulation.getEffectGraph();
		centerPanel.add(powerGraph);
		powerGraph.update();
		this.objectAdded = false;
		this.simulationStarted = false;
	}
	
	public void setSimulationTicket(SimulationTicket result) {
		this.ticket = result;
		
	}
	
	public void checkForResoult(){
		if (this.simulationFinished){
			System.out.println("Henter ut simuleringsdata");
			//databaseService.getSimulation(ticket.getDescriptionID(), new DescriptionsCallback(this));
		}
		else {
			System.out.println("simulation not finished");
		}
	}

	public void updateStatus(String string) {
		System.out.println(string);
		
	}
	
	@UiHandler("buttonLogout")
	void onButtonLogoutClick(ClickEvent event) {
		login = new Login();
		RootLayoutPanel.get().add(login);
	}
}