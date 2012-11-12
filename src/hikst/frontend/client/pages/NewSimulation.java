package hikst.frontend.client.pages;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.SplineGraf;
import hikst.frontend.client.callback.SimulationRequestCallback;
import hikst.frontend.client.callback.TreeCallback;
import hikst.frontend.shared.HikstObject;
import hikst.frontend.shared.SimulationRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Tree;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.FlowPanel;

public class NewSimulation extends HikstComposite {
	
	MainPage panelBack;
	private HikstObject simObject;
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
	@UiField Button button;
	private TreeCallback treeCallback;

	DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);

	interface NewSimulationUiBinder extends UiBinder<Widget, NewSimulation> {
	}

	public NewSimulation() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	public NewSimulation(HikstComposite hikstCompositeParent) {
		this();
//		if(((NewSimulation) hikstCompositeParent).fromDate == null)
//		Window.alert("date is null");
//		
//		if(((NewSimulation) hikstCompositeParent).fromDate.getValue() == null)
//			Window.alert("date value is null");
		
		fromDate.setValue(((NewSimulation) hikstCompositeParent).fromDate.getValue());
		toDate.setValue(((NewSimulation) hikstCompositeParent).toDate.getValue());
		intervall.setValue(((NewSimulation) hikstCompositeParent).intervall.getValue());
	}

	public NewSimulation(HikstComposite hikstCompositeParent, HikstObject simObject) {
		this(hikstCompositeParent);
		
		this.simObject = simObject;

		treeCallback = new TreeCallback(this);

		updateTree(simObject.getID());

	}
	
	private void updateTree(int id) {

		databaseService.loadObject(id, treeCallback);
	}

	@UiHandler("addObject")
	void onAddObjectClick(ClickEvent event) {
//		panel = new ViewObjects(this);
		RootLayoutPanel.get().add(new ViewObjects(this));
	}

	@UiHandler("back")
	void onBackClick(ClickEvent event) {
		panelBack = new MainPage();
		RootLayoutPanel.get().add(panelBack);
	}

	@UiHandler("buttonShowSpline")
	void onButtonShowSplineClick(ClickEvent event) {
		centerPanel.clear();
		//måtte kommentere ut denne etter at jeg endret spline klassen.
		//centerPanel.add(SplineGraf.createChart());
		System.out.println("Should show spline!!!");
	}
	@UiHandler("button")
	void onButtonClick(ClickEvent event) {
		databaseService.requestSimulation(new SimulationRequest(simObject.getID(),Long.parseLong(intervall.getValue()),fromDate.getValue().getTime(),toDate.getValue().getTime()), new SimulationRequestCallback());
}

	@UiHandler("emailAdmin")
	void onEmailAdminClick(ClickEvent event) {
		RootLayoutPanel.get().add(new EmailAdmin());
	}
}