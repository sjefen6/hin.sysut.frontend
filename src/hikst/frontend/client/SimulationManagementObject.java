package hikst.frontend.client;


import java.util.ArrayList;
import java.util.List;

import hikst.frontend.client.callback.SimulatorObjectCallback;
import hikst.frontend.client.pages.MyDockLayoutPanel;
import hikst.frontend.shared.ImpactDegree;
import hikst.frontend.shared.SimulatorObject;

import com.google.gwt.core.client.GWT;

//used by the client to change and manage an existing simulation object
public class SimulationManagementObject 
{
	private static SimulatorObject getSimulatorObject(SimulationManagementObject managementObject)
	{
		ArrayList<SimulatorObject> sons = managementObject.getSonsInSimulatorObjectFormat();
		
		return new SimulatorObject(managementObject.getName(), sons,managementObject.getEffect(),
				managementObject.getVoltage(),managementObject.getCurrent(),managementObject.getPower_consumption(),
				managementObject.getImpactDegree());
	}
	
	private DatabaseServiceAsync connection = GWT.create(DatabaseService.class);
	private boolean isNotInDatabase = true;
	
	private MyDockLayoutPanel panel;
	
	//object data
	private int id;
	private String name;
	private double effect;
	private double voltage;
	private double current;
	private double power_consumption;
	
	//impact degree data
	private boolean is_impacted_by_a_factor;
	private double impact_degree_percent;
	private int impact_degree_type_id;
	
	public ArrayList<SimulationManagementObject> sons = new ArrayList<SimulationManagementObject>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getEffect() {
		return effect;
	}

	public void setEffect(double effect) {
		this.effect = effect;
	}

	public double getVoltage() {
		return voltage;
	}

	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}

	public double getCurrent() {
		return current;
	}

	public void setCurrent(double current) {
		this.current = current;
	}

	public double getPower_consumption() {
		return power_consumption;
	}

	public void setPower_consumption(double power_consumption) {
		this.power_consumption = power_consumption;
	}

	public boolean isIs_impacted_by_a_factor() {
		return is_impacted_by_a_factor;
	}

	public void setIs_impacted_by_a_factor(boolean is_impacted_by_a_factor) {
		this.is_impacted_by_a_factor = is_impacted_by_a_factor;
	}

	public double getImpact_degree_percent() {
		return impact_degree_percent;
	}

	public void setImpact_degree_percent(double impact_degree_percent) {
		this.impact_degree_percent = impact_degree_percent;
	}

	public int getImpact_degree_type_id() {
		return impact_degree_type_id;
	}

	public void setImpact_degree_type_id(int impact_degree_type_id) {
		this.impact_degree_type_id = impact_degree_type_id;
	}

	public ImpactDegree getImpactDegree()
	{
		if(this.is_impacted_by_a_factor)
			return new ImpactDegree(this.impact_degree_percent,this.impact_degree_type_id);
		
		return null;
	}
	
	public SimulatorObject getSimulatorObject()
	{
		return SimulationManagementObject.getSimulatorObject(this);
	}
	
	public SimulationManagementObject(MyDockLayoutPanel panel)
	{
		this.isNotInDatabase = true;
		this.voltage = 50;
		this.current = 2;
		this.effect = 4;
		this.power_consumption = 100;
		this.is_impacted_by_a_factor = false;
		this.impact_degree_percent = 0.0;
		this.impact_degree_type_id = 0;
	}
	
	public SimulationManagementObject(int id)
	{
		connection.getSimulatorObject(id, new SimulatorObjectCallback(this));
		this.isNotInDatabase = false;
	}
	
	//use this to add a new simulation object to an existing one
	public void createNewSon()
	{
		sons.add(new SimulationManagementObject(panel));
	}
	
	public ArrayList<SimulatorObject> getSonsInSimulatorObjectFormat()
	{
		ArrayList<SimulatorObject> sonsInSimulatorObjectFormat = new ArrayList<SimulatorObject>();
		
		for(int i = 0; i<sons.size(); i++)
		{
			SimulationManagementObject son = sons.get(i);
			SimulatorObject sonInSimulationObjectFormat = SimulationManagementObject.getSimulatorObject(son);
			sonsInSimulatorObjectFormat.add(sonInSimulationObjectFormat);
		}
		
		return sonsInSimulatorObjectFormat;
	}
	
	//called by the Simulator - callback for loading an existing object that you want to manage
	public void load(SimulatorObject simulatorObject)
	{
		this.id = simulatorObject.getID();
		this.name = simulatorObject.getName();
		this.effect = simulatorObject.getEffect();
		this.power_consumption = simulatorObject.getEffect();
		this.current = simulatorObject.getCurrent();
		this.voltage = simulatorObject.getVoltage();
		this.isNotInDatabase = false;
		this.is_impacted_by_a_factor = simulatorObject.getImpact_degree() != null;
		
		if(this.is_impacted_by_a_factor)
		{
			this.impact_degree_percent = simulatorObject.getImpact_degree().getPercent();
			this.impact_degree_type_id = simulatorObject.getImpact_degree().getTypeID();
		}
		
		List<SimulatorObject> sons = simulatorObject.getSons();
		
		for(int i = 0; i<sons.size(); i++)
		{
			SimulationManagementObject son = new SimulationManagementObject(panel);
			son.load(sons.get(i));
			this.sons.add(son);
		}
	}
	
	/*public void save(MyDockLayoutPanel panel)
	{	
		if(this.isNotInDatabase)
		{
			connection.saveObject(getSimulatorObject(), new SaveSimulatorObjectCallback(panel));
		}
		else
		{
			connection.updateObject(id, this.getSimulatorObject(), new UpdateCallback());
		}
		
	}*/
	
	public void clear()
	{
		
	}
	
	public void delete()
	{
		clear();
	}
	
}
