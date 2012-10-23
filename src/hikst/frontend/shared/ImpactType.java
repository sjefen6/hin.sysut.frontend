package hikst.frontend.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class ImpactType implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int ID;
	public String name;
	
	public ArrayList<Integer> types = new ArrayList<Integer>();
	
	public ImpactType(){}

}
