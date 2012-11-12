package hikst.frontend.shared;

import java.io.Serializable;

public class ImpactDegree implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int object_id;
	public double percent;
	public int type_id;
	
	public ImpactDegree(){
		this.object_id = -1;
	}
	
	public ImpactDegree(int object_id){
		this.object_id = object_id;
	}
	
	public int getId(){
		return object_id;
	}
}
