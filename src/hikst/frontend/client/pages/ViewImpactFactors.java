package hikst.frontend.client.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.FlexTable;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.callback.ImpactFactorsCallback;
import hikst.frontend.client.callback.SimObjectsCallback;
import hikst.frontend.shared.ImpactType;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Label;

public class ViewImpactFactors extends Composite {

	Composite panel;
	NewObject NewObpanel;

	interface ViewImpactFactorsUiBinder extends
			UiBinder<Widget, ViewImpactFactors> {
	}

	private static ViewImpactFactorsUiBinder uiBinder = GWT
			.create(ViewImpactFactorsUiBinder.class);
	@UiField
	Button addImpButton;
	@UiField
	Button tilbakeButton;
	@UiField
	FlowPanel centerPanel;
	@UiField
	ListBox impactFactorType;
	@UiField Label ImpLabel;
	@UiField TextBox inputBox;



	private Composite parent;
	private DatabaseServiceAsync databaseService = GWT
			.create(DatabaseService.class);

	public ViewImpactFactors() {
		initWidget(uiBinder.createAndBindUi(this));
		initFactorListBox();
		
		centerPanel.add(impactFactorType);
	}

	private void initFactorListBox() {
		centerPanel.remove(impactFactorType);
		databaseService.getImpactTypes(new ImpactFactorsCallback(
				impactFactorType, parent));

		centerPanel.add(impactFactorType);
		inputBox.getElement().setAttribute("placeHolder", "ImpactFactor % ");

		impactFactorType.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
				
				int itemSelected = impactFactorType.getSelectedIndex();
				String itemStringSelected = impactFactorType.getValue(itemSelected);
				
				inputBox.setText(itemStringSelected);

//				int selectedIndex = impactFactorType.getSelectedIndex();
//				if (selectedIndex > 0)
//					
			}
		});
	}
	

	@UiHandler("tilbakeButton")
	void onButtontilbake(ClickEvent event) {
		NewObpanel = new NewObject(this);
		RootLayoutPanel.get().add(NewObpanel);
	}
	
	@UiHandler("addImpButton")
	void onAddClick(ClickEvent event) {
		
		inputBox.getText();
		
	}

}