package hikst.frontend.shared;

import java.io.Serializable;


public class SimObjectTree implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public boolean isEmpty = true;
	public SimObject rootObject = new SimObject();
	//public SimObject currentSelectedObject = rootObject;
	
	public void delete(SimObject simObject)
	{
		if(simObject == rootObject)
		{
			clear();
		}
		else
		{
			SimObject parent = simObject.Parent;
			
			parent.removeChild(simObject);
		}
	}	
	
	
	public void clear()
	{
		isEmpty = true;
		rootObject.clear();
	}
}
