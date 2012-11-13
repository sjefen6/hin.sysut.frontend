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
import hikst.frontend.client.callback.ImpactDegreeCallback;
import hikst.frontend.client.callback.ImpactFactorsCallback;
import hikst.frontend.client.callback.SaveObjectCallback;
import hikst.frontend.shared.HikstObject;
import hikst.frontend.shared.ImpactDegree;

import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.DoubleBox;

public class NewImpactDegree extends HikstComposite {

	private ImpactDegree impactDegree;
	
//	public double impFactor;

	interface NewImpactDegreeUiBinder extends
			UiBinder<Widget, NewImpactDegree> {
	}

	private static NewImpactDegreeUiBinder uiBinder = GWT
			.create(NewImpactDegreeUiBinder.class);
	@UiField
	Button addImpButton;
	@UiField
	Button tilbakeButton;
	@UiField
	FlowPanel centerPanel;
	@UiField
	ListBox impactFactorType;
	@UiField DoubleBox inputBox;

	private DatabaseServiceAsync databaseService = GWT
			.create(DatabaseService.class);
	
	public NewImpactDegree(HikstComposite hikstCompositeParent) {
		initWidget(uiBinder.createAndBindUi(this));
		initFactorListBox();
		this.hikstCompositeParent = hikstCompositeParent;
		centerPanel.add(impactFactorType);
		impactDegree = new ImpactDegree();
		
	}

	private void initFactorListBox() {
//		centerPanel.remove(impactFactorType);
		databaseService.getImpactTypes(new ImpactFactorsCallback(
				impactFactorType, hikstCompositeParent));

		centerPanel.add(impactFactorType);
	}
	
	public ImpactDegree getImpactDegree(){
		impactDegree.percent = inputBox.getValue();
		impactDegree.type_id = Integer.parseInt(impactFactorType.getValue(impactFactorType.getSelectedIndex()));
		return impactDegree;
	}

	@UiHandler("tilbakeButton")
	void onButtontilbake(ClickEvent event) {
		RootLayoutPanel.get().add(new NewObject((NewObject) hikstCompositeParent));
	}

	@UiHandler("addImpButton")
	void onAddClick(ClickEvent event) {
		NewObject panel = new NewObject((NewObject) hikstCompositeParent);
		panel.addImpactDegree(getImpactDegree());
		RootLayoutPanel.get().add(panel);
	}
}