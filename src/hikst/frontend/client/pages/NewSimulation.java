package hikst.frontend.client.pages;

import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.plaf.RootPaneUI;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.SplineGraf;
import hikst.frontend.client.callback.SimObjectTreeCallback;
import hikst.frontend.shared.SimObject;
import hikst.frontend.shared.SimObjectTree;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.javac.JsniCollector.Interval;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.FlowPanel;

public class NewSimulation extends Composite implements HasText {

	ViewObjects panel;
	MainPage panelBack;
	private static NewSimulationUiBinder uiBinder = GWT
			.create(NewSimulationUiBinder.class);
	@UiField Button back;
	@UiField Button addObject;
	@UiField DateBox fromDate;
	@UiField DateBox toDate;
	@UiField TextBox intervall;
	@UiField Button buttonShowSpline;
	@UiField FlowPanel eastPanel;
	@UiField FlowPanel centerPanel;
	@UiField Tree tree;
	SimObjectTree simulatorObject = new SimObjectTree();
	//SimulationManagementObject simManager = new SimulationManagementObject(this);
	SimObject selectedObject = null;
	//@UiField Button printIt;
	
	DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);
	
	interface NewSimulationUiBinder extends UiBinder<Widget, NewSimulation> {
	}

	private SimObjectTree simObjectTree;
	
	
	public void setSimObject(SimObjectTree simObject)
	{
		simObjectTree = simObject;
		updateView();
	}
	
	private void updateView(){
		
	}
	
	public NewSimulation() {
		initWidget(uiBinder.createAndBindUi(this));
		tree.addSelectionHandler(new SelectionHandler<TreeItem>()
				{

					@Override
					public void onSelection(SelectionEvent<TreeItem> event) {
						
						TreeItem treeItem = tree.getSelectedItem();
						
						Integer[] path = getPath(treeItem);
						
						SimObject selectedObject = simulatorObject.rootObject;
						
						for(int depth = path.length - 1; depth>= 0; depth--)
						{
							selectedObject = selectedObject.getChild(path[depth]);
						}
						
						
					}
			
				});
	}
	
	public NewSimulation(Composite parent, SimObject simObject){
		this();
		fromDate.setValue(((NewSimulation) parent).fromDate.getValue());
		toDate.setValue(((NewSimulation) parent).toDate.getValue());
		intervall.setValue(((NewSimulation) parent).intervall.getValue());
		simObjectTree = new SimObjectTree();
		selectedObject = simObject;
		simObjectTree.rootObject = simObject;
		
		updateTree();
		
	}
	
	@SuppressWarnings("deprecation")
	private void updateTree()
	{
//		tree.clear();
		
		CheckBox cb = new CheckBox(simulatorObject.rootObject.name);
		TreeItem rootItem = new TreeItem(cb);	
		rootItem.setText(simulatorObject.rootObject.name);
    	cb.setValue(true);
    	cb.addClickListener(new ClickListener()
    	{
			@Override
			public void onClick(Widget sender) {
					
			}
    	});
    	
    	
    	
    	
    	Iterator<SimObject> iterator = simulatorObject.rootObject.getChildIterator();
    	
    	while(iterator.hasNext())
    	{
    		SimObject entry = iterator.next();
    		
    		rootItem.addItem(addChildren(entry));
    	}
    	tree.addItem(rootItem);
	}
	
	@SuppressWarnings("deprecation")
	private TreeItem addChildren(SimObject simObject)
	{
		CheckBox cb = new CheckBox(simObject.name);
		TreeItem rootItem = new TreeItem(cb);
		
    	cb.setValue(true);
    	cb.addClickListener(new ClickListener()
    	{
			@Override
			public void onClick(Widget sender) {
				
			}
    		
    	});
    	
    	Iterator<SimObject> iterator = simObject.getChildIterator();
    	
    	if(iterator.hasNext())
    	{
    		SimObject entry = iterator.next();
    		
    		rootItem.addItem(addChildren(entry));
    	}
    	
    	return rootItem;
	}

	
	private Integer[] getPath(TreeItem treeItem)
	{
		TreeItem parentItem = null;
		ArrayList<Integer> path = new ArrayList<Integer>();
		
		while(treeItem.getParentItem() != null)
		{
			parentItem = treeItem.getParentItem();
			
			int index = parentItem.getChildIndex(treeItem);
			
			path.add(index);
			
			treeItem = parentItem;
		}
		
		Integer[] returnPath = new Integer[path.size()];
		path.toArray(returnPath);
		//Window.alert(path.toString());
		return returnPath;
	}
	
	public NewSimulation(int id)
	{
		databaseService.loadObject(id, new SimObjectTreeCallback(this));
	}

	@UiHandler("addObject")
	void onAddObjectClick(ClickEvent event) {
		//RootLayoutPanel.get().add(new NewObject());
		panel = new ViewObjects(this);
		RootLayoutPanel.get().add(panel);
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub

	}
	@UiHandler("back")
	void onBackClick(ClickEvent event) {
		RootLayoutPanel.get().add(new MainPage());
		panelBack = new MainPage();
		RootLayoutPanel.get().add(panelBack);
	}


	@UiHandler("buttonShowSpline")
	void onButtonShowSplineClick(ClickEvent event) {
		centerPanel.clear();
		centerPanel.add(SplineGraf.createChart());
		System.out.println("Should show spline!!!");
	}
	

}