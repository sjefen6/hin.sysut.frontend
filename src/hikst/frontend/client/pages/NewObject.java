package hikst.frontend.client.pages;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.callback.SaveObjectCallback;
import hikst.frontend.shared.HikstObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.Maps;
import com.google.gwt.maps.client.control.LargeMapControl;
import com.google.gwt.maps.client.control.MapTypeControl;
import com.google.gwt.maps.client.event.MapClickHandler;
import com.google.gwt.maps.client.geom.LatLng;
import com.google.gwt.maps.client.overlay.Marker;
import com.google.gwt.maps.client.overlay.MarkerOptions;
import com.google.gwt.maps.client.overlay.Overlay;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;

public class NewObject extends HikstComposite {

	ViewImpactFactors viewImpPanel;

	private HikstObject o = new HikstObject();

	private static NewObjectUiBinder uiBinder = GWT
			.create(NewObjectUiBinder.class);

	@UiField
	TextBox name;
	@UiField
	TextBox effect;
	@UiField
	TextBox voltage;
	@UiField
	TextBox current;
	@UiField
	TextBox latitude;
	@UiField
	TextBox longitude;
	@UiField
	TextBox self_temperature;
	@UiField
	TextBox target_temperature;
	@UiField
	TextBox base_area;
	@UiField
	TextBox base_height;
	@UiField
	TextBox heat_loss_rate;
	@UiField
	Button back;
	@UiField
	Button saveObject;
	@UiField
	Button addChildObject;
	@UiField
	Button addUsagePattern;
	@UiField
	Button showMap;
	@UiField
	Button addImpactButton;
	@UiField
	AbsolutePanel mapsPanel;
	@UiField
	FlowPanel eastPanel;
	@UiField
	Label effectLabel;
	@UiField
	Label nameLabel;
	@UiField
	Label voltageLabel;
	@UiField
	Label currentLabel;
	@UiField
	Label latLabel;
	@UiField
	Label longLabel;
	@UiField
	Label starttempLabel;
	@UiField
	Label targettempLabel;
	@UiField
	Label baseareaLabel;
	@UiField
	Label baseheightLabel;
	@UiField
	Label heatlossLabel;

	MapWidget map;

	private DatabaseServiceAsync databaseService = GWT
			.create(DatabaseService.class);

	interface NewObjectUiBinder extends UiBinder<Widget, NewObject> {
	}

	/**
	 * Main constructor
	 * 
	 * @hikstCompositeParent - this when going upwords,
	 *                       hikstCompositeParent.getHikstCompositeParent() when
	 *                       going back
	 */
	public NewObject(HikstComposite hikstCompositeParent) {
		this.hikstCompositeParent = hikstCompositeParent;
		o = new HikstObject();
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * Constructor used when returning from Objects list with a child object
	 * 
	 * @param parent
	 * @param childObject
	 */
	public NewObject(HikstComposite hikstCompositeParent,
			HikstObject childObject) {
		this(hikstCompositeParent.getHikstCompositeParent());
		o = ((NewObject) hikstCompositeParent).getObject();
		o.sons.add(childObject.getID());
		setValues();
	}

	/**
	 * Constructor used when returning from NewUsagePattern with a
	 * usagePatternID
	 * 
	 * @param parent
	 * @param childObject
	 */
	public NewObject(HikstComposite hikstCompositeParent, int usagePatternID) {
		this(hikstCompositeParent.getHikstCompositeParent());
		o = ((NewObject) hikstCompositeParent).getObject();
		o.usage_pattern_ID = usagePatternID;
		setValues();
	}

	public HikstObject getObject() {
		o.name = name.getText();
		// o.effect = effect.getValue();
		try {
			o.effect = Double.parseDouble(effect.getText());
		} catch (NumberFormatException e) {
			o.effect = Double.NaN;
		}
		try {
			o.voltage = Double.parseDouble(voltage.getText());
		} catch (NumberFormatException e) {
			o.voltage = Double.NaN;
		}

		try {
			o.current = Double.parseDouble(current.getText());
		} catch (NumberFormatException e) {
			o.current = Double.NaN;
		}

		try {
			o.latitude = Double.parseDouble(latitude.getText());
		} catch (NumberFormatException e) {
			o.latitude = Double.NaN;
		}
		try {
			o.longitude = Double.parseDouble(longitude.getText());
		} catch (NumberFormatException e) {
			o.longitude = Double.NaN;
		}
		try {
			o.self_temperature = Double.parseDouble(self_temperature.getText());
		} catch (NumberFormatException e) {
			o.self_temperature = Double.NaN;
		}
		try {
			o.target_temperature = Double.parseDouble(target_temperature
					.getText());
		} catch (NumberFormatException e) {
			o.target_temperature = Double.NaN;
		}
		try {
			o.base_area = Double.parseDouble(base_area.getText());
		} catch (NumberFormatException e) {
			o.base_area = Double.NaN;
		}
		try {
			o.base_height = Double.parseDouble(base_height.getText());
		} catch (NumberFormatException e) {
			o.base_height = Double.NaN;
		}
		try {
			o.heat_loss_rate = Double.parseDouble(heat_loss_rate.getText());
		} catch (NumberFormatException e) {
			o.heat_loss_rate = Double.NaN;
		}

		return o;
	}

	private void setValues() {
		name.setValue(o.name);
		if (o.effect.equals(Double.NaN)) {
			effect.setValue("");
		} else {
			// effect.setValue(o.effect.toString());
		}
		if (o.voltage.equals(Double.NaN)) {
			voltage.setValue("");
		} else {
			voltage.setValue(o.voltage.toString());
		}
		if (o.current.equals(Double.NaN)) {
			current.setValue("");
		} else {
			current.setValue(o.current.toString());
		}
		if (o.latitude.equals(Double.NaN)) {
			latitude.setValue("");
		} else {
			latitude.setValue(o.latitude.toString());
		}
		if (o.longitude.equals(Double.NaN)) {
			longitude.setValue("");
		} else {
			longitude.setValue(o.longitude.toString());
		}
		if (o.self_temperature.equals(Double.NaN)) {
			self_temperature.setValue("");
		} else {
			self_temperature.setValue(o.self_temperature.toString());
		}
		if (o.target_temperature.equals(Double.NaN)) {
			target_temperature.setValue("");
		} else {
			target_temperature.setValue(o.self_temperature.toString());
		}
		if (o.base_area.equals(Double.NaN)) {
			base_area.setValue("");
		} else {
			base_area.setValue(o.base_area.toString());
		}
		if (o.base_height.equals(Double.NaN)) {
			base_height.setValue("");
		} else {
			base_height.setValue(o.base_height.toString());
		}
		if (o.heat_loss_rate.equals(Double.NaN)) {
			heat_loss_rate.setValue("");
		} else {
			heat_loss_rate.setValue(o.heat_loss_rate.toString());
		}
	}

	@UiHandler("addChildObject")
	void onAddObjectClick(ClickEvent event) {
		RootLayoutPanel.get().add(new ViewObjects(this));
	}

	@UiHandler("addImpactButton")
	void onAddImpactClick(ClickEvent event) {
		RootLayoutPanel.get().add(new ViewImpactFactors());
	}

	@UiHandler("addUsagePattern")
	void onNewUsagePatternClick(ClickEvent event) {
		RootLayoutPanel.get().add(new NewUsagePattern(this));
	}

	@UiHandler("latitude")
	void onLatitudeClick(ClickEvent event) {

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

				// NumberFormat fmt = NumberFormat.getFormat("#.0000000#");
				latitude.setText(String.valueOf((int) (point.getLatitude() * 1000000f)));
				longitude.setText(String.valueOf((int) (point.getLongitude() * 1000000f)));

				MarkerOptions opt = MarkerOptions.newInstance();
				opt.setDraggable(true);

				if (overlay != null && overlay instanceof Marker) {
					sender.removeOverlay(overlay);
				} else {
					sender.addOverlay(new Marker(point));

				}
			}
		});

		// latitude.setEnabled(false);
		// longitude.setEnabled(false);
		mapsPanel.add(map);
		// Add the map to the HTML host page
	}

	@UiHandler("showMap")
	void onshowMapClick(ClickEvent event) {

		Maps.loadMapsApi("", "2", false, new Runnable() {
			public void run() {
				buildUi();
			}
		});
	}

	@UiHandler("back")
	void onBackClick(ClickEvent event) {
		RootLayoutPanel.get().add(new ViewObjects(this));
	}

	@UiHandler("saveObject")
	void onSaveObject(ClickEvent event) {
		if (name.getValue().equals("Name")) {
			Window.alert("Change Name!");
		} else {
			getObject();
			o.effect.isNaN();
			databaseService.saveObject(o, new SaveObjectCallback(o));
			onBackClick(event);
		}
	}
}