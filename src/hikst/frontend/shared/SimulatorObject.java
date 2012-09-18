package hikst.frontend.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class SimulatorObject implements Serializable
{
	
	private int id;
	private String name;
	private List<SimulatorObject> sons;
	private double effect;
	private double voltage;
	private double current;
	private double power_consumption;
	private ImpactDegree impact_degree;
	private boolean existsInDatabase;
	
	public String getName()
	{
		return name;
	}
	
	public boolean existsInDB()
	{
		return this.existsInDatabase;
	}
	
	public List<SimulatorObject> Sons()
	{
		return sons;
	}
	
	
	public boolean hasSons()
	{
		return sons.size() > 0;
	}
	
	//needed for serializing
	public SimulatorObject()
	{
		
	}
	
	//dummy constructor, used for debugging.....
	public SimulatorObject(String name, ArrayList<SimulatorObject> sons)
	{
		this.name = name;
		this.sons = sons;
		this.existsInDatabase = false;
	}
	
	//objects that has no sons. an electrical article 
	public SimulatorObject(int id,String name, double effect, double voltage, double current, double power_consumption)
	{
		this.id = id;
		this.name = name;
		this.effect = effect;
		this.voltage = voltage;
		this.current = current;
		this.power_consumption = power_consumption;
		this.sons = new ArrayList<SimulatorObject>();
		this.existsInDatabase = true;
	}
	
	//objects that has no sons. an electrical article 
	public SimulatorObject(String name, double effect, double voltage, double current, double power_consumption)
	{
		this.name = name;
		this.effect = effect;
		this.voltage = voltage;
		this.current = current;
		this.power_consumption = power_consumption;
		this.sons = new ArrayList<SimulatorObject>();
		this.existsInDatabase = false;
	}
	
	//used by the "simulator-management-object"-class
	public SimulatorObject(int id,String name, List<SimulatorObject> sons,
			double effect, double voltage, double current,
			double power_consumption, ImpactDegree impact_degree) {
		super();
		this.id = id;
		this.name = name;
		this.sons = sons;
		this.effect = effect;
		this.voltage = voltage;
		this.current = current;
		this.power_consumption = power_consumption;
		this.impact_degree = impact_degree;
		this.existsInDatabase = true;
	}
	
	public SimulatorObject(String name, List<SimulatorObject> sons,
			double effect, double voltage, double current,
			double power_consumption, ImpactDegree impact_degree) {
		super();
		this.name = name;
		this.sons = sons;
		this.effect = effect;
		this.voltage = voltage;
		this.current = current;
		this.power_consumption = power_consumption;
		this.impact_degree = impact_degree;
		this.existsInDatabase = false;
	}

	public List<SimulatorObject> getSons() {
		return sons;
	}

	public double getEffect() {
		return effect;
	}

	public double getVoltage() {
		return voltage;
	}

	public double getCurrent() {
		return current;
	}

	public double getPower_consumption() {
		return power_consumption;
	}

	public ImpactDegree getImpact_degree() {
		return impact_degree;
	}
	
	public int getID()
	{
		return id;
	}
}
