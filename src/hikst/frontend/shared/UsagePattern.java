package hikst.frontend.shared;

import java.io.Serializable;

public class UsagePattern implements Serializable{
	private int id;
	public String name;
	public int[] pattern = new int[24];
	public boolean actual;
	
	public UsagePattern() {}
	
	public int getID(){
		return id;
	}
}