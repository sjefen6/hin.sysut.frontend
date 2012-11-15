package hikst.frontend.client.pages;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.callback.SimulationRequestCallback;
import hikst.frontend.client.callback.TreeCallback;
import hikst.frontend.shared.HikstObject;
import hikst.frontend.shared.SimulationRequest;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
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
	FlowPanel eastPanel;
	@UiField
	FlowPanel centerPanel;
	public @UiField
	Tree tree;
	@UiField 
	Button save;
	@UiField 
	FlowPanel westPanel;
	
	private TreeCallback treeCallback;
	
	TextBox range = new TextBox();
	TextBox input = new TextBox();
	
	private String rangeValue, inputValue;
	

	DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);

	interface NewSimulationUiBinder extends UiBinder<Widget, NewSimulation> {
	}

	public NewSimulation() {
		initWidget(uiBinder.createAndBindUi(this));
		initRangeField();
		westPanel.add(back);
		westPanel.add(save);
		
	}
	
	public NewSimulation(NewSimulation hikstCompositeParent) {
		this();
		fromDate.setValue((hikstCompositeParent).fromDate.getValue());
		toDate.setValue((hikstCompositeParent).toDate.getValue());
		intervall.setValue((hikstCompositeParent).intervall.getValue());
		
	}
	
	private void initRangeField(){
		
		range = new TextBox();
		
		range.getElement().setAttribute("type", "range");
		range.getElement().setAttribute("min", "1000");
		range.getElement().setAttribute("max", "86400000");
		range.getElement().setAttribute("step", "100000");
		range.getElement().setAttribute("style" ,"width: 400px; height: 20px;");
	//	range.getElement().setAttribute(name, value)
		range.addChangeHandler(rangeChanged());
		westPanel.add(range);
		
		input = new TextBox();
		
		input.getElement().setAttribute("type", "number");
		input.getElement().setAttribute("min", "1000");
		input.getElement().setAttribute("max", "86400000");
		input.getElement().setAttribute("step", "100000");
		input.getElement().setAttribute("style", "width: 100px;");
		input.addChangeHandler(inputChanged());
		westPanel.add(input);
	}
	
	private ChangeHandler rangeChanged(){
		return new ChangeHandler() {
			
			@Override
			public void onChange(ChangeEvent event) {
				// TODO Auto-generated method stub
				
				rangeValue = range.getValue();
				input.setText(rangeValue);
				
			}
		};
	}
	
	private ChangeHandler inputChanged() {
		return new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				
				inputValue = input.getValue();
				range.setText(inputValue);
				
			}
		};
	}

	public NewSimulation(NewSimulation hikstCompositeParent, HikstObject simObject) {
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

	@UiHandler("save")
	void onButtonClick(ClickEvent event) {
		databaseService.requestSimulation(new SimulationRequest(simObject.getID(),Long.parseLong(intervall.getValue()),fromDate.getValue().getTime(),toDate.getValue().getTime()), new SimulationRequestCallback());
}

	@UiHandler("emailAdmin")
	void onEmailAdminClick(ClickEvent event) {
		RootLayoutPanel.get().add(new EmailAdmin());
	}
}