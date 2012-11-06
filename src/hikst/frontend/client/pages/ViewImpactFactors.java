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
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.callback.ImpactDegreeCallback;
import hikst.frontend.client.callback.ImpactFactorsCallback;
import hikst.frontend.client.callback.SaveObjectCallback;
import hikst.frontend.shared.HikstObject;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.DoubleBox;

public class ViewImpactFactors extends HikstComposite {

	HikstComposite panel;
	NewObject NewObpanel;
	public double impFactor;

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
	@UiField
	Label ImpLabel;
	@UiField DoubleBox inputBox;

	private Composite parent;
	private DatabaseServiceAsync databaseService = GWT
			.create(DatabaseService.class);

	private HikstObject hikstObject;
	
	public ViewImpactFactors(HikstObject hikstObject) {
		initWidget(uiBinder.createAndBindUi(this));
		initFactorListBox();
		this.hikstObject = hikstObject;
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
				String itemStringSelected = impactFactorType
						.getValue(itemSelected);
				

			}
		});
	}

	@UiHandler("tilbakeButton")
	void onButtontilbake(ClickEvent event) {
		goBack();
	}
	
	private void goBack()
	{
		NewObpanel = new NewObject((NewObject) hikstCompositeParent);
		RootLayoutPanel.get().add(NewObpanel);
	}

	@UiHandler("addImpButton")
	void onAddClick(ClickEvent event) {

		impFactor = inputBox.getValue();
		int type_id = Integer.parseInt(impactFactorType.getValue(impactFactorType.getSelectedIndex()));
		
		if(hikstObject.getID() == null){
			databaseService.saveObject(hikstObject, new SaveObjectCallback(hikstObject));
		}
		else{
		databaseService.addImpactDegree(impFactor,
				hikstObject.getID(),
				type_id ,
				new ImpactDegreeCallback());
		goBack();
		}
	}

}