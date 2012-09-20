package hikst.frontend.client;

public class SimObjectTree 
{
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
