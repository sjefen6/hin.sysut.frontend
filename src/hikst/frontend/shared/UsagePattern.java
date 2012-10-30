package hikst.frontend.shared;

public class UsagePattern {
	private int id;
	public String name;
	public int[] pattern = new int[24];
	public boolean actual;
	
	public UsagePattern() {}
	
	public int getID(){
		return id;
	}
}