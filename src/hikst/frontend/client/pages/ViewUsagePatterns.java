package hikst.frontend.client.pages;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.callback.HikstObjectsCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.Widget;

public class ViewUsagePatterns extends HikstComposite {
	
	private HikstComposite panel;
	interface ViewUsagePatternsUiBinder extends UiBinder<Widget, ViewUsagePatterns> {
	}

	private static ViewUsagePatternsUiBinder uiBinder = GWT
	.create(ViewUsagePatternsUiBinder.class);
	@UiField ScrollPanel centerPanel;
	@UiField FlexTable flexyTable;
	@UiField Button newUsagePattern;
	@UiField Button backButton;
	
	private DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);

	ClickHandler createObjectButtonClickHandler = new ClickHandler()
	{

		@Override
		public void onClick(ClickEvent event) {
			
		}
		
	};
	
	Button createSimObjectButton = new Button("Create object",createObjectButtonClickHandler);
	
	public ViewUsagePatterns(HikstComposite parent) {
		initWidget(uiBinder.createAndBindUi(this));
		this.parent = parent;
		initTable();
	}
	
	private void initTable()
	{		
		centerPanel.remove(flexyTable);
		databaseService.getSimObjects(new HikstObjectsCallback(flexyTable, parent));
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

