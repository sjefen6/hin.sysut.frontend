package hikst.frontend.client.pages;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.callback.SimObjectsCallback;
import hikst.frontend.client.pages.NewObject.NewObjectUiBinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlowPanel;

public class ViewObjects extends Composite {

	//VerticalPanel panel = new VerticalPanel();
	//FlexTable objectTable;
	NewObject newObjectPanel;
	interface ViewObjectsUiBinder extends UiBinder<Widget, ViewObjects> {
	}


	private static ViewObjectsUiBinder uiBinder = GWT
	.create(ViewObjectsUiBinder.class);
	@UiField FlowPanel centerPanel;
	@UiField FlexTable flexyTable;
	@UiField Button buttonSave;
	private DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);

	
	ClickHandler createObjectButtonClickHandler = new ClickHandler()
	{

		@Override
		public void onClick(ClickEvent event) {
			
		}
		
	};
	
	Button createSimObjectButton = new Button("Create object",createObjectButtonClickHandler);
	
	public ViewObjects() {
		initWidget(uiBinder.createAndBindUi(this));
		//initWidget(uiBinder.createAndBindUi(this));
//		initWidget(panel);
		//initButtons();
		initTable();
	}
	
	private void initTable()
	{
		//flexyTable = new FlexTable();
		
		databaseService.getSimObjects(new SimObjectsCallback(flexyTable));
	
		centerPanel.add(flexyTable);
	}
	@UiHandler("buttonSave")
	void onButtonSave(ClickEvent event){
		newObjectPanel = new NewObject();
		RootLayoutPanel.get().add(newObjectPanel);
	}
	
	//private void initButtons()
	//{
		//HorizontalPanel buttonPanel = new HorizontalPanel();
		//buttonPanel.add(createSimObjectButton);

		//centerPanel.add(buttonPanel);
	//}
}


