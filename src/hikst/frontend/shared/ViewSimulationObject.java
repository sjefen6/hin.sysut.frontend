package hikst.frontend.shared;

import java.io.Serializable;

public class ViewSimulationObject implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer ID;		//Sim-description ID
	public String Object_Name;
	public String Status_Name;
	
	public Integer getID(){
		return ID;
	}
	
	public void setID(int ID){
		
		this.ID = ID;
	}
	
	
	public ViewSimulationObject(){}

}
