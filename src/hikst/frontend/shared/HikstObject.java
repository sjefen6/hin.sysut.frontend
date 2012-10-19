package hikst.frontend.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class HikstObject implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int ID;
	public String name;
	public float effect;
	public float voltage;
	public float current;
	public int usage_pattern_ID;
	public double latitude;
	public double longitude;
	public double self_temperature;
	public double target_temperature;
	public double base_area;
	public double base_height;
	public double heat_loss_rate;
	public ArrayList<Integer> sons = new ArrayList<Integer>();
	
	public HikstObject(){}
}
