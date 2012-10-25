package hikst.frontend.client.pages;

import sun.misc.Compare;
import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.callback.HikstObjectsCallback;
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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlowPanel;

public class ViewObjects extends Composite {

	//VerticalPanel panel = new VerticalPanel();
	//FlexTable objectTable;
	
	
	Composite panel;
	interface ViewObjectsUiBinder extends UiBinder<Widget, ViewObjects> {
	}


	private static ViewObjectsUiBinder uiBinder = GWT
	.create(ViewObjectsUiBinder.class);
	@UiField ScrollPanel centerPanel;
	@UiField FlexTable flexyTable;
	@UiField Button newObject;
	@UiField Button backButton;
	
	private Composite parent;
	private DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);

	
	ClickHandler createObjectButtonClickHandler = new ClickHandler()
	{

		@Override
		public void onClick(ClickEvent event) {
			
		}
		
	};
	
	Button createSimObjectButton = new Button("Create object",createObjectButtonClickHandler);
	
	public ViewObjects(Composite parent) {
		initWidget(uiBinder.createAndBindUi(this));
		//initWidget(uiBinder.createAndBindUi(this));
//		initWidget(panel);
		//initButtons();
		this.parent = parent;
		initTable();
	}
	
	private void initTable()
	{
		//flexyTable = new FlexTable();
		
		centerPanel.remove(flexyTable);
		databaseService.getSimObjects(new HikstObjectsCallback(flexyTable, parent));
		centerPanel.add(flexyTable);
		
	//	centerPanel.add(flexyTable);
	}
	
	@UiHandler("newObject")
	void onButtonSave(ClickEvent event){
		panel = new NewObject(this);
		RootLayoutPanel.get().add(panel);
	}
	
	@UiHandler("backButton")
	void onBackButtonClick(ClickEvent event) {
		this.removeFromParent();
	}
}

