package hikst.frontend.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class HikstObject implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer ID;
	public String name;
	public Double effect;
	public Double voltage;
	public Double current;
	public Integer usage_pattern_ID;
	
	public Double latitude;
	public Double longitude;
	public Double self_temperature;
	public Double target_temperature;
	public Double base_area;
	public Double base_height;
	public Double heat_loss_rate;
	public ArrayList<Integer> sons = new ArrayList<Integer>();
	
	public Integer getID(){
		return ID;
	}
	
	/**
	 * Must only be set in the save object callback*/
	public void setID(int ID){
		this.ID = ID;
	}
	
	public HikstObject(){}
}
