package hikst.frontend.client.pages;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.callback.HikstObjectsCallback;
import hikst.frontend.client.callback.SimulationsListCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Button;

public class ViewSimulations extends Composite {

	private static ViewSimulationsUiBinder uiBinder = GWT
			.create(ViewSimulationsUiBinder.class);

	@UiField
	FlowPanel centerPanel;
	@UiField
	FlexTable SimListTable;
	@UiField
	Button backButton;

	MainPage mainpage;

	private DatabaseServiceAsync databaseService = GWT
			.create(DatabaseService.class);

	interface ViewSimulationsUiBinder extends UiBinder<Widget, ViewSimulations> {
	}

	public ViewSimulations() {
		initWidget(uiBinder.createAndBindUi(this));

		initSimListTable();
	}

	private void initSimListTable() {

		centerPanel.remove(SimListTable);
		 databaseService.getViewSimulationObjects(new SimulationsListCallback(SimListTable));

		centerPanel.add(SimListTable);
	}

	@UiHandler("backButton")
	void onButtontilbake(ClickEvent event) {
		mainpage = new MainPage();
		RootLayoutPanel.get().add(mainpage);
	}
}
