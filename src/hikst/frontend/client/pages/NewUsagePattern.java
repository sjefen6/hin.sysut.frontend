package hikst.frontend.client.pages;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.shared.SimObject;
import hikst.frontend.shared.UsagePattern;

import com.google.gwt.core.client.GWT;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;

public class NewUsagePattern extends HikstComposite implements HasText {

	ViewObjects panel;

	Composite parent;
	private UsagePattern u = new UsagePattern();

	interface NewUsagePatternUiBinder extends UiBinder<Widget, NewUsagePattern> {
	}
	
	private static NewUsagePatternUiBinder uiBinder = GWT
			.create(NewUsagePatternUiBinder.class);

	@UiField TextBox name;
	@UiField TextBox c00;

	@UiField Button back;
	@UiField Button saveObject;
	@UiField FlowPanel eastPanel;
	@UiField Label nameLabel;


	MapWidget map;

	private DatabaseServiceAsync databaseService = GWT
			.create(DatabaseService.class);

	/**
	 * Main constructor
	 */
	public NewUsagePattern(HikstComposite parent2) {
		//this.parent = usagePattern;
		u = new UsagePattern();
		initWidget(uiBinder.createAndBindUi(this));
		setToRange();
	}
	
	/**
	 * Constructor used when returning from Objects list with a child object
	 * 
	 * @param parent
	 * @param childObject
	 */
	public NewUsagePattern(HikstComposite parent, SimObject childObject) {
		this(parent);
	}

	/**
	 * Sets all clock inputs to ranges
	 */
	private void setToRange(){
		c00.getElement().setAttribute("type", "range");
		c00.getElement().setAttribute("min", "0");
		c00.getElement().setAttribute("max", "100");
		c00.getElement().setAttribute("step", "1");
	}

	public UsagePattern getObject() {
		u.name = name.getText();
//		try {
//			o.effect = Double.parseDouble(effect.getValue());
//		} catch (NumberFormatException e) {
//			o.effect = Double.NaN;
//		}

		return u;
	}

	private void setValues() {
		name.setValue(u.name);
//		if (o.effect == Double.NaN) {
//			effect.setValue("");
//		} else {
//			effect.setValue(o.effect.toString());
//		}
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

//	@UiHandler("showMap")
//	void onshowMapClick(ClickEvent event) {
//
//		Maps.loadMapsApi("", "2", false, new Runnable() {
//			public void run() {
//				buildUi();
//			}
//		});
//	}
//
//	@UiHandler("back")
//	void onBackClick(ClickEvent event) {
//		mapsPanel.clear();
//		eastPanel.clear();
//		RootLayoutPanel.get().add(new NewSimulation());
//		panel = new ViewObjects(this);
//		RootLayoutPanel.get().add(panel);
//	}
//
//	@UiHandler("saveObject")
//	void onSaveObject(ClickEvent event) {
//		if (name.getValue().equals("Name")) {
//			Window.alert("Change Name!");
//		} else {
//			databaseService.saveObject(o, new SaveObjectCallback());
//		}
//	}
}
