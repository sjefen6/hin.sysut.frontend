package hikst.frontend.shared;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ImpactDegree implements Serializable
{
	private double percent;
	private int type_id;
	
	public double getPercent()
	{
		return percent;
	}
	
	public int getTypeID()
	{
		return type_id;
	}
	
	public ImpactDegree(){}
	
	public ImpactDegree(double percent, int type_id)
	{
		this.percent = percent;
		this.type_id = type_id;
	}
}
