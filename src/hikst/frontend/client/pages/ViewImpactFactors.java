package hikst.frontend.client.pages;

import com.google.gwt.core.client.GWT;
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
import com.google.gwt.user.client.ui.ListBox;

public class ViewImpactFactors extends Composite {

	Composite panel;
	NewObject NewObpanel;

	interface ViewImpactFactorsUiBinder extends
			UiBinder<Widget, ViewImpactFactors> {
	}

	private static ViewImpactFactorsUiBinder uiBinder = GWT
			.create(ViewImpactFactorsUiBinder.class);
	@UiField
	Button createNewButton;
	@UiField
	Button tilbakeButton;
	@UiField
	ScrollPanel scrollPanel;
	@UiField
	ListBox impactFactorType;

	private Composite parent;
	private DatabaseServiceAsync databaseService = GWT
			.create(DatabaseService.class);

	public ViewImpactFactors() {
		initWidget(uiBinder.createAndBindUi(this));
		//impactFactorType.initFactorListBox();
	}

	private void initFactorListBox() {
		scrollPanel.remove(impactFactorType);
//		databaseService.getImpactTypes(new ImpactFactorsCallback(ImpactTypeBox,
//				parent));
		scrollPanel.add(impactFactorType);
	}

	@UiHandler("tilbakeButton")
	void onButtontilbake(ClickEvent event) {
		NewObpanel = new NewObject(this);
		RootLayoutPanel.get().add(NewObpanel);
	}

}
