package hikst.frontend.client.pages;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.Graph;
import hikst.frontend.client.Simulation;
import hikst.frontend.client.SimulationManagementObject;
import hikst.frontend.client.callback.DescriptionsCallback;
import hikst.frontend.client.callback.StatusCallback;
import hikst.frontend.client.callback.SimulationRequestCallback;
import hikst.frontend.shared.Description;
import hikst.frontend.shared.SimulationRequest;
import hikst.frontend.shared.SimulationTicket;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.TextArea;
 
public class MyDockLayoutPanel extends Composite {

  private boolean loggedin = false;
  private boolean updated = false;
  private String user1 = "Navn";
  private String userpass = "Passord";
  Simulation simulation;

  
	//interface MyUiBinder extends UiBinder<Widget, MyDockLayoutPanel>{}
	public SimulationManagementObject simManager = new SimulationManagementObject(this);
    Graph g;
	private static MyDockLayoutPanelUiBinder uiBinder = GWT
    .create(MyDockLayoutPanelUiBinder.class);
    
    @UiField Button Test1;
    @UiField FlowPanel centerPanel;
    @UiField TextBox nameField;
    @UiField TextBox user;
    @UiField TextBox pass;
    @UiField Button login;
    @UiField Label aa;
    @UiField Button update;
    @UiField Tree tree;
    @UiField Button remHouses;
    @UiField Button databaseCall;
    @UiField TextBox wattField;
    @UiField TextBox voltField;
    @UiField TextBox intervalField;
    @UiField DateBox fromDateField;
    @UiField DateBox toDateField;
    @UiField
	public TextArea statusField;
    int yAxis = 1200;
    long interval;
	private int objectCounter = 0;
    private DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);
    Date toDate,fromDate;
    public SimulationTicket ticket;
    public boolean objectAdded = false;
    public boolean simulationStarted = false;
    public boolean simulationFinished = false;
   
    
    public MyDockLayoutPanel(){
    	initWidget(uiBinder.createAndBindUi(this)); 
    	aa.setText("Du m\u00E5 logge inn");
    }

    @UiHandler("nameField")
    void onClick(ClickEvent event){
    	nameField.setText("DemoObject");
    }
    @UiHandler("wattField")
    void onClickwattField(ClickEvent event){
    	wattField.setText("40");
    }
    @UiHandler("voltField")
    void onClickvoltField(ClickEvent event){
    	voltField.setText("230");
    }
    @UiHandler("intervalField")
    void onClickintervalField(ClickEvent event){
    	intervalField.setText("86400000");
    }
    
    @UiHandler("update")
    void onClick4(ClickEvent e){
    	
    	this.statusField.setText("Trying to save object to database...");
    	
    	updated = true;
    	String wattS = wattField.getText();
    	String name = nameField.getText();
    	String voltS = voltField.getText();
    	String intervalString = intervalField.getText();
    	toDate = toDateField.getValue();
    	fromDate = fromDateField.getValue();
    	
    	interval = Long.parseLong(intervalString);
    	double watt = Integer.parseInt(wattS);
    	double volt = Integer.parseInt(voltS);
    	
    	simManager.setEffect(watt);
    	simManager.setVoltage(volt);
    	simManager.setName(name);
    	//simManager.save(this);
    	    	
    	CheckBox cb = new CheckBox("Object "+(objectCounter = objectCounter + 1)+" - "+name);
    	cb.setValue(true);
    	TreeItem object = new TreeItem(cb);
    	TreeItem info = new TreeItem("Info");
    	info.addItem("To date: "+toDate);
    	info.addItem("fom date: "+fromDate);
    	info.addItem("interval: "+ interval);
    	object.addItem(info);
    	TreeItem electricCrap = new TreeItem("Electric crap:");
    	electricCrap.addItem("Watt: "+ watt);
    	electricCrap.addItem("Volt: "+ volt);
    	tree.addItem(electricCrap);
    	tree.addItem(object);
    	initWidget(tree);
    	
    }
    
    interface MyDockLayoutPanelUiBinder extends
            UiBinder<Widget, MyDockLayoutPanel> {
    }
 
	@UiHandler("Test1")
    void onClick2(ClickEvent e) {
		//centerPanel.clear();
		if (loggedin ==  true){
			if(updated == true){
			
			if(this.objectAdded)
			{	
				ticket = new SimulationTicket(-1,false,0);
				databaseService.requestSimulation(
						new SimulationRequest(
								simManager.getId(),
								interval,
								fromDate.getTime(),
								toDate.getTime()
								),
								new SimulationRequestCallback(this));
			
				simulationStarted = true;
				
				this.statusField.setText("Simulation request has been sent");
			}
			else
			{
				Window.alert("Vent til objektet er lagt inn i databasen");
			}
				//g = new Graph();
			//centerPanel.clear();
			//centerPanel.add(g);
		    //g.update();
			}
			else {
				 Window.alert("Du m\u00E5 legge til hus før du kan simulere");
			}
			
		}		
		else{
			Window.alert("Du m\u00E5 logge inn f\u00F8r du kan gj\u00F8re en simulasjon!");
		}
    }

	
	
	@UiHandler("login") void onClick1(ClickEvent ee){
		
		if (user.getText().toString().equals(user1) && pass.getText().toString().equals(userpass)){
			loggedin = true;
			aa.setText("");
			aa.setText("Du er logget inn " + user.getText().toString());
		}
		else if (user.getText().toString().equals("") || pass.getText().toString().equals("")){
			aa.setText("Du glemte \u00E5 skrive passord eller brukernavn");
		}
		else {
			aa.setText("Feil brukernavn eller passord");
			}
			
			}
	
	
	@UiHandler("user")
	void onUserClick(ClickEvent event) {
		user.setText("");
	}
	@UiHandler("pass")
	void onPassClick(ClickEvent event) {
		pass.setText("");
	}
	@UiHandler("remHouses")
	void onRemHousesClick(ClickEvent event) {
		//simManager.clear();
		//tree.clear();
		//updated = false;
		if(ticket == null)
		{
			this.statusField.setText("The simulation ticket is null..");
			return;
		}
		
		if(this.simulationStarted)
		{
			this.statusField.setText("Checking simulation status...");
			databaseService.getSimulationStatus(ticket,new StatusCallback(this));
		}
		else
		{
			this.statusField.setText("Simulation has not started yet");
		}
		
	}
	
	public void updateStatus(String status)
	{
		this.statusField.setText("Status: "+status);
	}
	
	public void setData(Description description)
    {
    	simulation = new Simulation(description);
    	centerPanel.clear();
    	Graph powerGraph = simulation.getEffectGraph();
		centerPanel.add(powerGraph);
		powerGraph.update();
		this.objectAdded = false;
		this.simulationStarted = false;
    }
	
	public void setSimulationTicket(SimulationTicket ticket)
	{
		this.ticket = ticket;
	}
	
	MyDockLayoutPanel panel;
	@UiHandler("databaseCall")
	void onDatabaseCallClick(ClickEvent event) {
		
		if(this.simulationFinished)
		{
			System.out.println("Henter ut simuleringsdata fra database...");
			this.statusField.setText("Retrieving simulation data from database..");
			databaseService.getSimulation(ticket.getDescriptionID(), new DescriptionsCallback(this));
		}
		else
		{
			this.statusField.setText("Simulation not finished...");
		}
	}
}