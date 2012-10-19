package hikst.frontend.client.pages;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.callback.SaveObjectCallback;
import hikst.frontend.shared.HikstObject;
import hikst.frontend.shared.SimObject;
import hikst.frontend.shared.SimObjectTree;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.control.MapTypeControl;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.FlowPanel;

public class NewObject extends Composite implements HasText/*, LocationCallback*/ {

	ViewObjects panel;
//	SimObjectTree simulatorObject = new SimObjectTree();
//	SimulationManagementObject simManager = new SimulationManagementObject(this);
//	SimObject selectedSimObject = null;
	Composite parent;
	private HikstObject o = new HikstObject();
	
	private static NewObjectUiBinder uiBinder = GWT
			.create(NewObjectUiBinder.class);
	
	@UiField TextBox name;
	@UiField TextBox effect;
	@UiField TextBox voltage;
	@UiField TextBox current;
	@UiField TextBox latitude;
	@UiField TextBox longitude;
	@UiField TextBox self_temperature;
	@UiField TextBox target_temperature;
	@UiField TextBox base_area;
	@UiField TextBox base_height;
	@UiField TextBox heat_loss_rate;
	
	@UiField Button back;
	@UiField Button saveObject;
	@UiField Button addChildObject;
	@UiField Button addUsagePattern;
	@UiField Button showMap;
	
	@UiField AbsolutePanel mapsPanel;
	
	@UiField FlowPanel eastPanel;
	

	MapWidget map;

	private DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);

	interface NewObjectUiBinder extends UiBinder<Widget, NewObject> {
	}

	/**
	 * Main constructor
	 */
	public NewObject(Composite parent) {
		this.parent = parent;
		o = new HikstObject();
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * Constructor used when returning from Objects list with a child object
	 * 
	 * @param parent
	 * @param childObject
	 */
	public NewObject(NewObject parent, SimObject childObject){
		this(parent.parent);
		o = ((NewObject) parent).getObject();
		o.sons.add(childObject.getID());
		setValues();
	}
	
	public HikstObject getObject(){
		o.name = name.getValue();
		o.effect = Float.parseFloat(effect.getValue());
		o.voltage = Float.parseFloat(voltage.getValue());
		o.current = Float.parseFloat(current.getValue());
//		o.usage_pattern_ID = Integer.parseInt(usage_pattern_ID.getValue());
		o.latitude = Double.parseDouble(latitude.getValue());
		o.longitude = Double.parseDouble(longitude.getValue());
		o.self_temperature = Double.parseDouble(self_temperature.getValue());
		o.target_temperature = Double.parseDouble(target_temperature.getValue());
		o.base_area = Double.parseDouble(base_area.getValue());
		o.base_height = Double.parseDouble(base_height.getValue());
		o.heat_loss_rate = Double.parseDouble(heat_loss_rate.getValue());
		
		return o;
	}
	
	private void setValues(){
		name.setValue(o.name);
		effect.setValue(Float.toString(o.effect));
		voltage.setValue(Float.toString(o.voltage));
		current.setValue(Float.toString(o.current));
		latitude.setValue(Double.toString(o.latitude));
		longitude.setValue(Double.toString(o.longitude));
		self_temperature.setValue(Double.toString(o.self_temperature));
		target_temperature.setValue(Double.toString(o.self_temperature));
		base_area.setValue(Double.toString(o.base_area));
		base_height.setValue(Double.toString(o.base_height));
		heat_loss_rate.setValue(Double.toString(o.heat_loss_rate));
	}
	
	@UiHandler("addChildObject")
	void onAddObjectClick(ClickEvent event) {
		//RootLayoutPanel.get().add(new NewObject());
		panel = new ViewObjects(this);
		RootLayoutPanel.get().add(panel);
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
	          longitude.setText(String.valueOf((int)(point.getLongitude() * 1000000f)));

	    MarkerOptions opt = MarkerOptions.newInstance();
	    opt.setDraggable(true);
	    
	    if (overlay != null && overlay instanceof Marker) {
	          sender.removeOverlay(overlay);
	        } else {
	          sender.addOverlay(new Marker(point));
	        }
	      }
	    });
	    
//	    latitude.setEnabled(false);
//	    longitude.setEnabled(false);
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
		mapsPanel.clear();
		eastPanel.clear();
		RootLayoutPanel.get().add(new NewSimulation());
		panel = new ViewObjects(this);
		RootLayoutPanel.get().add(panel);
	}
	
	@UiHandler("saveObject")
	void onSaveObject(ClickEvent event){
		if (name.getValue().equals("Name")){
			Window.alert("Change Name!");
		} else {
			databaseService.saveObject(o, new SaveObjectCallback());
		}
	}
}
