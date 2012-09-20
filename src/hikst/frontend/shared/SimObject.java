package hikst.frontend.shared;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class SimObject implements Serializable
{	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8606553727129015435L;
	private int id;
	public String name = "empty";
	public float impactDegree = 1;
	public float effect = 1;
	public float volt = 1;
	public int longitude = 1;
	public int latitude = 1;
	public int usagePattern = 1;
	
	public SimObject Parent = null;
	
	private ArrayList<SimObject> simulatorObjects = new ArrayList<SimObject>();
	private boolean fromDatabase;
	
	public boolean isFromDatabase()
	{
		return fromDatabase;
	}
	
	public int getID()
	{
		return id;
	}
	
	public SimObject(int id)
	{
		this.id = id;
		fromDatabase = true;
	}
	
	public SimObject()
	{
		
	}
	
	public boolean hasChildren()
	{
		return simulatorObjects.isEmpty();
	}
	
	public void clear()
	{
		name = "Empty";
		impactDegree = 0;
		effect = 0;
		volt = 0;
		longitude = 0;
		latitude = 0;
		usagePattern = 0;
		
		
		simulatorObjects.clear();
	}
	
	public void addChild(SimObject simObject)
	{
		simObject.Parent = this;
		
		simulatorObjects.add(simObject);
	}
	
	public SimObject getChild(int index)
	{
		return simulatorObjects.get(index);
	}
	
	public void removeChild(SimObject simObject)
	{
		simulatorObjects.remove(simObject);
	}
	
	public Iterator<SimObject> getChildIterator()
	{
		return simulatorObjects.iterator();
	}
	
	public ArrayList<SimObject> getChildren()
	{
		return simulatorObjects;
	}
}
