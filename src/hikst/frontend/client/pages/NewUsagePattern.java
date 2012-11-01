package hikst.frontend.client.pages;

import hikst.frontend.shared.SimObject;
import hikst.frontend.shared.UsagePattern;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.Label;

public class NewUsagePattern extends HikstComposite {

	private UsagePattern u = new UsagePattern();

	interface NewUsagePatternUiBinder extends UiBinder<Widget, NewUsagePattern> {
	}

	private static NewUsagePatternUiBinder uiBinder = GWT
			.create(NewUsagePatternUiBinder.class);

	@UiField TextBox name;
	@UiField FlexTable flexyTable;
	@UiField
	Button back;
	@UiField
	Button saveObject;
	@UiField
	FlowPanel eastPanel;
	@UiField
	Label nameLabel;
	@UiField
	FlowPanel centerPanel;

	TextBox[] clock = new TextBox[24];

	/**
	 * Main constructor
	 */
	public NewUsagePattern(HikstComposite hikstCompositeParent) {
		this.hikstCompositeParent = hikstCompositeParent;
		u = new UsagePattern();
		initWidget(uiBinder.createAndBindUi(this));
		generateFlexyTable();
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
	private void generateFlexyTable() {
		centerPanel.remove(flexyTable);
		flexyTable.clear();

		for (int i = 0; i <= 23; i++) {
			flexyTable.setWidget(0, i, new Label(i + ":00"));
		}
		
		for (int i = 0; i <= 23; i++) {
			clock[i] = new TextBox();
			clock[i].getElement().setAttribute("type", "range");
			clock[i].getElement().setAttribute("min", "0");
			clock[i].getElement().setAttribute("max", "100");
			clock[i].getElement().setAttribute("step", "1");
			clock[i].getElement().setAttribute("style", "width: 20px;height: 300px; -webkit-appearance: slider-vertical;");
			flexyTable.setWidget(1, i, clock[i]);
		}
		
		centerPanel.add(flexyTable);

	}

	public UsagePattern getObject() {
		u.name = name.getText();
		// try {
		// o.effect = Double.parseDouble(effect.getValue());
		// } catch (NumberFormatException e) {
		// o.effect = Double.NaN;
		// }

		return u;
	}

	private void setValues() {
		name.setValue(u.name);
		// if (o.effect == Double.NaN) {
		// effect.setValue("");
		// } else {
		// effect.setValue(o.effect.toString());
		// }
	}

	// @UiHandler("back")
	// void onBackClick(ClickEvent event) {
	// mapsPanel.clear();
	// eastPanel.clear();
	// RootLayoutPanel.get().add(new NewSimulation());
	// panel = new ViewObjects(this);
	// RootLayoutPanel.get().add(panel);
	// }
	//
	// @UiHandler("saveObject")
	// void onSaveObject(ClickEvent event) {
	// if (name.getValue().equals("Name")) {
	// Window.alert("Change Name!");
	// } else {
	// databaseService.saveObject(o, new SaveObjectCallback());
	// }
	// }
}
