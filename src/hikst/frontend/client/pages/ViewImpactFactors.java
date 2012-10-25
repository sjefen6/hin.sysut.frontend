package hikst.frontend.client.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.callback.ImpactFactorsCallback;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class ViewImpactFactors extends HikstComposite {

	HikstComposite panel;
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

		impactFactorType.addChangeHandler(new ChangeHandler() {

			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub

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
		//kode her
		
	}

}