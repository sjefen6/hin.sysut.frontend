package hikst.frontend.shared;

import java.io.Serializable;

public class ImpactDegree implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	public double percent;
	public int type_id;
	
	public ImpactDegree(){
		this.id = -1;
	}
	
	public ImpactDegree(int id){
		this.id = id;
	}
	
	public int getId(){
		return id;
	}
}
