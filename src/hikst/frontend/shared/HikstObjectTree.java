package hikst.frontend.shared;

import java.io.Serializable;
import java.util.ArrayList;

import com.google.gwt.user.client.ui.TreeItem;

public class HikstObjectTree implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<HikstObjectTree> sons;
	private HikstObject item;
	
	public HikstObjectTree()
	{
		super();
		sons = new ArrayList<HikstObjectTree>();
	}
	
	public HikstObjectTree(HikstObject item)
	{
		super();
		this.item = item;
		sons = new ArrayList<HikstObjectTree>();
	}
	
	public void setItem(HikstObject item)
	{
		this.item = item;
	}
	
	public TreeItem getTree()
	{
		TreeItem item = new TreeItem();
		item.setText(this.item.name);
		item.setUserObject(this.item);
		
		for(HikstObjectTree treeItem : sons)
		{
			item.addItem(treeItem.getTree());
		}
		
		return item;
	}
	
	public void addSon(HikstObjectTree HikstObjectTree){
		sons.add(HikstObjectTree);
	}
	

	
}
