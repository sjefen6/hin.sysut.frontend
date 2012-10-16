package hikst.frontend.client.pages;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.tools.ant.taskdefs.PathConvert.MapEntry;


import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.callback.StoreObjectCallback;
import hikst.frontend.shared.SimObject;
import hikst.frontend.shared.SimObjectTree;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.maps.client.InfoWindowContent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.control.MapTypeControl;
import com.google.gwt.maps.client.geocode.LocationCallback;
import com.google.gwt.maps.client.geocode.Placemark;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.event.MarkerClickHandler;
import com.google.gwt.maps.client.event.MarkerDragEndHandler;
import com.google.gwt.maps.client.event.MarkerDragStartHandler;
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
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TreeItem;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;

public class NewObject extends Composite implements HasText/*, LocationCallback*/ {

	NewSimulation panel;
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
	@UiField Button slettObjektButton;
	@UiField Button updateObjectButton;
	@UiField AbsolutePanel mapsPanel;
	@UiField Button showMap;

	MapWidget map;
	@UiField FlowPanel eastPanel;

	private DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);

	interface NewObjectUiBinder extends UiBinder<Widget, NewObject> {
	}

	public NewObject() {
		initWidget(uiBinder.createAndBindUi(this));
		
//		tree.addSelectionHandler(new SelectionHandler<TreeItem>()
//				{
//
//					@Override
//					public void onSelection(SelectionEvent<TreeItem> event) {
//						
//						TreeItem treeItem = tree.getSelectedItem();
//						
//						Integer[] path = getPath(treeItem);
//						
//						SimObject selectedObject = simulatorObject.rootObject;
//						
//						for(int depth = path.length - 1; depth>= 0; depth--)
//						{
//							selectedObject = selectedObject.getChild(path[depth]);
//						}
//						
//						//simObject = selectedObject;
//						updateMenu();
//					}
//			
//				});
	}
	
	@UiHandler("impactFactor")
	void onimpactFactorClick(ClickEvent event){
		impactFactor.setText("1");
		effect.setText("500");
		volt.setText("230");
		name.setText("Hus");
		usagePattern.setText("2");
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
	
	@UiHandler("latitude")
	void onlatitudeClick(ClickEvent event){
	 
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
		mapsPanel.clear();
		eastPanel.clear();
		RootLayoutPanel.get().add(new NewSimulation());
		panel = new NewSimulation();
		RootLayoutPanel.get().add(panel);
	}
	
	@UiHandler("saveObject")
	void onSaveObject(ClickEvent event){
	
		
		databaseService.saveObject(simulatorObject, new StoreObjectCallback());
	}
}
