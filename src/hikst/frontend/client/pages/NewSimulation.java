package hikst.frontend.client.pages;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.SplineGraf;
import hikst.frontend.client.callback.TreeCallback;
import hikst.frontend.shared.HikstObject;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.FlowPanel;

public class NewSimulation extends HikstComposite {

	Composite panel;
	MainPage panelBack;
	private EmailAdmin mailPanel;
	private static NewSimulationUiBinder uiBinder = GWT
			.create(NewSimulationUiBinder.class);
	@UiField
	Button back;
	@UiField
	Button addObject;
	@UiField
	DateBox fromDate;
	@UiField
	DateBox toDate;
	@UiField
	TextBox intervall;
	@UiField
	Button buttonShowSpline;
	@UiField
	FlowPanel eastPanel;
	@UiField
	FlowPanel centerPanel;
	public @UiField
	Tree tree;
	private TreeCallback treeCallback;

	DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);

	interface NewSimulationUiBinder extends UiBinder<Widget, NewSimulation> {
	}

	public NewSimulation() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	private void updateTree(int id) {

		databaseService.loadObject(id, treeCallback);
	}

	public NewSimulation(HikstComposite parent, HikstObject simObject) {
		this();
		fromDate.setValue(((NewSimulation) parent).fromDate.getValue());
		toDate.setValue(((NewSimulation) parent).toDate.getValue());
		intervall.setValue(((NewSimulation) parent).intervall.getValue());

		treeCallback = new TreeCallback(this);

		updateTree(simObject.getID());

	}

	@UiHandler("addObject")
	void onAddObjectClick(ClickEvent event) {
		panel = new ViewObjects(this.parent);
		RootLayoutPanel.get().add(panel);
	}

	@UiHandler("back")
	void onBackClick(ClickEvent event) {
		panelBack = new MainPage();
		RootLayoutPanel.get().add(panelBack);
	}

	@UiHandler("buttonShowSpline")
	void onButtonShowSplineClick(ClickEvent event) {
		centerPanel.clear();
		centerPanel.add(SplineGraf.createChart());
		System.out.println("Should show spline!!!");
	}

	@UiHandler("emailAdmin")
	void onEmailAdminClick(ClickEvent event) {
		mailPanel = new EmailAdmin();
		RootLayoutPanel.get().add(mailPanel);
	}
}