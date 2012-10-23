package hikst.frontend.client.pages;

import sun.misc.Compare;
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
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlowPanel;

public class ViewUsagePatterns extends Composite {
	
	Composite panel;
	interface ViewUsagePatternsUiBinder extends UiBinder<Widget, ViewUsagePatterns> {
	}

	private static ViewUsagePatternsUiBinder uiBinder = GWT
	.create(ViewUsagePatternsUiBinder.class);
	@UiField ScrollPanel centerPanel;
	@UiField FlexTable flexyTable;
	@UiField Button newUsagePattern;
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
	
	public ViewUsagePatterns(Composite parent) {
		initWidget(uiBinder.createAndBindUi(this));
		this.parent = parent;
		initTable();
	}
	
	private void initTable()
	{		
		centerPanel.remove(flexyTable);
		databaseService.getSimObjects(new SimObjectsCallback(flexyTable, parent));
		centerPanel.add(flexyTable);
	}
	
	@UiHandler("newUsagePattern")
	void onButtonSave(ClickEvent event){
		panel = new NewObject(this);
		RootLayoutPanel.get().add(panel);
	}
	
	@UiHandler("backButton")
	void onBackButtonClick(ClickEvent event) {
		this.removeFromParent();
	}
}

