package hikst.frontend.client.pages;

import java.util.ArrayList;
import java.util.Iterator;


import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.callback.StoreObjectCallback;
import hikst.frontend.shared.SimObject;
import hikst.frontend.shared.SimObjectTree;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.control.MapTypeControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.AbsolutePanel;

public class NewObject extends Composite implements HasText {

	ObjectMenu panel;
	SimObjectTree simulatorObject = new SimObjectTree();
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
	@UiField Button updateObjectButton;
	@UiField TextBox updateNameButton;
	@UiField TextBox updateImpactDegreeButton;
	@UiField TextBox updateEffectButton;
	@UiField TextBox updateVoltButton;
	@UiField TextBox updateLongitudeButton;
	@UiField TextBox updateLatitudeButton;
	@UiField TextBox updateUsagePatternButton;
	@UiField AbsolutePanel mapsPanel;
	MapWidget map;
	@UiField Button showMap;

	private DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);

	interface NewObjectUiBinder extends UiBinder<Widget, NewObject> {
	}

	public NewObject() {
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
						
						//simObject = selectedObject;
						updateMenu();
					}
			
				});
	}
	
	

	@UiHandler("impactFactor")
	void onimpactFactorClick(ClickEvent event){
		impactFactor.setText("1");
		effect.setText("1");
		volt.setText("1");
		name.setText("1");
		usagePattern.setText("1");
	}
	@UiHandler("latitude")
	void onLatitudeClick(ClickEvent event){
		
		Maps.loadMapsApi("", "2", false, new Runnable() {
		      public void run() {
		        buildUi();
		      }
		    });
	}
	
	private void buildUi() {
	    // Open a map centered on Cawker City, KS USA
	    LatLng startPos = LatLng.newInstance(68.4384404, 17.4260552);
	    
	    final MapWidget map = new MapWidget(startPos, 2);
	    map.setSize("100%", "100%");
	    // Add some controls for the zoom level
	    map.addControl(new LargeMapControl());
	    map.addControl(new MapTypeControl());
	    
	    map.addMapClickHandler(new MapClickHandler() {
	        public void onClick(MapClickEvent e) {
	          map.clearOverlays();
	        	
	        	MapWidget sender = e.getSender();
	          Overlay overlay = e.getOverlay();
	          LatLng point = e.getLatLng();

	          //NumberFormat fmt = NumberFormat.getFormat("#.0000000#");
	          latitude.setText(String.valueOf((int)(point.getLatitude() * 1000000f)));
	          longtitude.setText(String.valueOf((int)(point.getLongitude() * 1000000f)));

	    MarkerOptions opt = MarkerOptions.newInstance();
	    opt.setDraggable(true);
	    
	    if (overlay != null && overlay instanceof Marker) {
	          sender.removeOverlay(overlay);
	        } else {
	          sender.addOverlay(new Marker(point));
	        }
	      }
	    });
	    
	    latitude.setEnabled(false);
	    longtitude.setEnabled(false);
	
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
	
	@UiHandler("showMap")
	void onshowMapClick(ClickEvent event){
		
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
	
		
		databaseService.saveObject(simulatorObject, new StoreObjectCallback());
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
				selectedSimObject.addChild(newObject);
			}
			
			simulatorObject.isEmpty = false;
			selectedSimObject = newObject;
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
    	
    	//initWidget(tree);
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
    	
    	//h¯ h¯
    	Iterator<SimObject> iterator = simObject.getChildIterator();
    	
    	if(iterator.hasNext())
    	{
    		SimObject entry = iterator.next();
    		
    		rootItem.addItem(addChildren(entry));
    	}
    	
    	return rootItem;
	}
	
	private void updateMenu()
	{
		updateNameButton.setText(selectedSimObject.name);
		updateImpactDegreeButton.setText(String.valueOf(selectedSimObject.impactDegree));
		updateEffectButton.setText(String.valueOf(selectedSimObject.effect));
		updateVoltButton.setText(String.valueOf(selectedSimObject.volt));
		updateLongitudeButton.setText(String.valueOf(selectedSimObject.longitude));
		updateLatitudeButton.setText(String.valueOf(selectedSimObject.latitude));
		updateUsagePatternButton.setText(String.valueOf(selectedSimObject.usagePattern));
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
	
	@UiHandler("slettObjektButton")
	void onSlettObjektButtonClick(ClickEvent event) {
			
		simulatorObject.delete(selectedSimObject);
		updateTree();
		selectedSimObject = simulatorObject.rootObject;
		updateMenu();
	}

	@UiHandler("updateObjectButton")
	void onUpdateObjectButtonClick(ClickEvent event) {
		
		if(selectedSimObject != null)
		{
			try
			{
				selectedSimObject.name = updateNameButton.getText();
				selectedSimObject.effect = Float.parseFloat(updateEffectButton.getText());
				selectedSimObject.volt = Float.parseFloat(updateVoltButton.getText());
				selectedSimObject.impactDegree = Integer.parseInt(updateImpactDegreeButton.getText());
				selectedSimObject.latitude = Integer.parseInt(updateLatitudeButton.getText());
				selectedSimObject.longitude = Integer.parseInt(updateLongitudeButton.getText());
				selectedSimObject.usagePattern = Integer.parseInt(updateUsagePatternButton.getText());
			}
			catch(Exception ex)
			{
				Window.alert("Parsing exception :"+ex.getMessage());
			}
			
			updateTree();
		}
	}
}
