package hikst.frontend.client;

import java.util.ArrayList;
import java.util.Iterator;


import hikst.frontend.shared.Description;
import hikst.frontend.shared.SimulationRequest;
import hikst.frontend.shared.SimulationTicket;
import hikst.frontend.shared.SimulatorObject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.event.MarkerDragStartHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class NewObject extends Composite implements HasText {

	ObjectMenu panel;
	SimulatorObjectTree simulatorObject = new SimulatorObjectTree();
	//SimulationManagementObject simManager = new SimulationManagementObject(this);
	SimObject selectedSimObject = null;
	
	private static NewObjectUiBinder uiBinder = GWT
			.create(NewObjectUiBinder.class);
	@UiField TextBox impactFactor;
	@UiField TextBox effect;
	@UiField TextBox volt;
	@UiField TextBox name;
	@UiField TextBox longtitude;
	@UiField TextBox latitude;
	@UiField TextBox usagePattern;
	@UiField Button back;
	@UiField Button addObject;
	@UiField Button saveObject;
	@UiField Tree tree;
	@UiField Button slettObjektButton;
	@UiField AbsolutePanel mapsPanel;
	
	private DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);
	
	
	interface NewObjectUiBinder extends UiBinder<Widget, NewObject> {
	}

	public NewObject() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("impactFactor")
	void onimpactFactorClick(ClickEvent event){
		impactFactor.setText("1");
		effect.setText("1");
		volt.setText("1");
		name.setText("1");
		longtitude.setText("1");
		latitude.setText("1");
		usagePattern.setText("1");
	}
	
	private void buildUi() {
	    // Open a map centered on Cawker City, KS USA
	    LatLng startPos = LatLng.newInstance(68.4384404, 17.4260552);
	    
	    final MapWidget map = new MapWidget(startPos, 2);
	    map.setSize("100%", "100%");
	    // Add some controls for the zoom level
	    map.addControl(new LargeMapControl());
	    
	
	    MarkerOptions opt = MarkerOptions.newInstance();
	    opt.setDraggable(true);
	   
	    // Add a marker
	    map.addOverlay(new Marker(startPos, opt));
	    // Add an info window to highlight a point of interest
	    map.getInfoWindow().open(map.getCenter(),
	        new InfoWindowContent("Selve byen!"));
	
	    mapsPanel.add(map);
	    // Add the map to the HTML host page
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
	@UiHandler("latitude")
	void onlatitudeClick(ClickEvent event){
	   Maps.loadMapsApi("", "2", false, new Runnable() {
		      public void run() {
		        buildUi();
		      }
		    });
	}

	@UiHandler("back")
	void onBackClick(ClickEvent event) {
		RootLayoutPanel.get().add(new ObjectMenu());
		panel = new ObjectMenu();
		RootLayoutPanel.get().add(panel);
	}
	
	@UiHandler("saveObject")
	void onSaveObject(ClickEvent event){
	
		SimulatorObject simObject = new SimulatorObject();
		databaseService.saveObject(simObject, new StoreObjectCallback());
	}

	@SuppressWarnings("deprecation")
	@UiHandler("addObject")
	void onAddObject(ClickEvent event){
	
		String objectName = name.getText();
		String impactDegree = impactFactor.getText();
		String objectEffect = effect.getText();
		String objectVolt = volt.getText();
		String objectLongitude = longtitude.getText();
		String objectLatitude = latitude.getText();
		String objectUsagePattern = usagePattern.getText();
		
		try
		{
			float floatEffect = Float.parseFloat(objectEffect);
			float floatVolt = Float.parseFloat(objectVolt);
			int intLongitude = Integer.parseInt(objectLongitude);
			int intLatitude = Integer.parseInt(objectLatitude);
			int intUsagePattern = Integer.parseInt(objectUsagePattern);
			float intImpactDegree = Float.parseFloat(impactDegree);
			
			SimObject newObject = new SimObject();
			newObject.name = objectName;
			newObject.effect = floatEffect;
			newObject.volt = floatVolt;
			newObject.longitude = intLongitude;
			newObject.latitude = intLatitude;
			newObject.usagePattern = intUsagePattern;
			newObject.impactDegree = intImpactDegree;
			
			if(simulatorObject.isEmpty)
			{
				
				simulatorObject.rootObject = newObject;
			}
			else
			{
				
				selectedSimObject.simulatorObjects.add(newObject);
			}
			
			simulatorObject.isEmpty = false;
			
			updateTree();
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings("deprecation")
	private void updateTree()
	{
		tree.clear();
		
		CheckBox cb = new CheckBox(simulatorObject.rootObject.name);
		TreeItem rootItem = new TreeItem(cb);
	
//		rootItem.addItem("Effect: "+simulatorObject.rootObject.effect);
//		rootItem.addItem("Volt: "+simulatorObject.rootObject.volt);
//		rootItem.addItem("Longitude: "+simulatorObject.rootObject.longitude);
//		rootItem.addItem("Latitude: "+simulatorObject.rootObject.latitude);
//		rootItem.addItem("Usagepattern: "+simulatorObject.rootObject.usagePattern);
//		rootItem.addItem("Impact degree :"+simulatorObject.rootObject.impactDegree);
//		
		tree.addSelectionHandler(new SelectionHandler<TreeItem>()
				{

					@Override
					public void onSelection(SelectionEvent<TreeItem> event) {
						
						TreeItem treeItem = tree.getSelectedItem();
						
						Integer[] path = getPath(treeItem);
						
						SimObject selectedObject = simulatorObject.rootObject;
						
						for(int depth = path.length - 1; depth>= 0; depth--)
						{
							selectedObject = selectedObject.simulatorObjects.get(path[depth]);
						}
						
						selectedSimObject = selectedObject;
						
						updateMenu();
					}
			
				});
		
    	cb.setValue(true);
    	cb.addClickListener(new ClickListener()
    	{

			@Override
			public void onClick(Widget sender) {
					
				
			}
    		
    		
    	});
    	
    	
    	
    	
    	//hø hø
    	Iterator<SimObject> iterator = simulatorObject.rootObject.simulatorObjects.iterator();
    	
    	while(iterator.hasNext())
    	{
    		SimObject entry = iterator.next();
    		
    		rootItem.addItem(addChildren(entry));
    	}
    	
    	tree.addItem(rootItem);
    	
    	//initWidget(tree);
	}
	
	@SuppressWarnings("deprecation")
	private TreeItem addChildren(SimObject simObject)
	{
		CheckBox cb = new CheckBox(simObject.name);
		TreeItem rootItem = new TreeItem(cb);
//		rootItem.addItem("Effect: "+simObject.effect);
//		rootItem.addItem("Volt: "+simObject.volt);
//		rootItem.addItem("Longitude: "+simObject.longitude);
//		rootItem.addItem("Latitude: "+simObject.latitude);
		//rootItem.addItem("Usagepattern: "+simObject.usagePattern);
		//rootItem.addItem("Impact degree :"+simulatorObject.rootObject.impactDegree);
		
    	cb.setValue(true);
    	cb.addClickListener(new ClickListener()
    	{

			@Override
			public void onClick(Widget sender) {
				
		
			}
    		
    	});
    	
    	//hø hø
    	Iterator<SimObject> iterator = simObject.simulatorObjects.iterator();
    	
    	if(iterator.hasNext())
    	{
    		SimObject entry = iterator.next();
    		
    		rootItem.addItem(addChildren(entry));
    	}
    	
    	return rootItem;
	}
	
	private void updateMenu()
	{
		name.setText(selectedSimObject.name);
		impactFactor.setText(String.valueOf(selectedSimObject.impactDegree));
		effect.setText(String.valueOf(selectedSimObject.effect));
		volt.setText(String.valueOf(selectedSimObject.volt));
		longtitude.setText(String.valueOf(selectedSimObject.longitude));
		usagePattern.setText(String.valueOf(selectedSimObject.usagePattern));
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
		Window.alert(path.toString());
		return returnPath;
	}
	
	private class SimulatorObjectTree 
	{
		public boolean isEmpty = true;
		public SimObject rootObject = new SimObject();
		//public SimObject currentSelectedObject = rootObject;
		
		public void delete(SimObject simObject)
		{
			if(simObject == rootObject)
			{
				clear();
			}
			else
			{
				SimObject parent = simObject.Parent;
				
				parent.simulatorObjects.remove(parent);
			}
		}	
		
		
		public void clear()
		{
			isEmpty = true;
			rootObject.clear();
		}
	}
	
	private class SimObject
	{	
		public String name = "empty";
		public float impactDegree = 1;
		public float effect = 1;
		public float volt = 1;
		public int longitude = 1;
		public int latitude = 1;
		public int usagePattern = 1;
		
		public SimObject Parent = null;
		
		private ArrayList<SimObject> simulatorObjects = new ArrayList<SimObject>();
		
		public boolean hasChildren()
		{
			return simulatorObjects.isEmpty();
		}
		
		public void clear()
		{
			name = "Empty";
			impactDegree = 0;
			effect = 0;
			volt = 0;
			longitude = 0;
			latitude = 0;
			usagePattern = 0;
			
			
			simulatorObjects.clear();
		}
		
		public void addChild(SimObject simObject)
		{
			simObject.Parent = this;
			
			simulatorObjects.add(simObject);
		}
		
		public SimObject getChild(int index)
		{
			return simulatorObjects.get(index);
		}
	}

	
	@UiHandler("slettObjektButton")
	void onSlettObjektButtonClick(ClickEvent event) {
			
		simulatorObject.delete(selectedSimObject);
		updateTree();
		selectedSimObject = simulatorObject.rootObject;
		updateMenu();
	}
}
