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

public class ViewObjects extends HikstComposite {
<<<<<<< HEAD
	
=======

	private HikstComposite panel;

>>>>>>> alex/master
	interface ViewObjectsUiBinder extends UiBinder<Widget, ViewObjects> {
	}

	private static ViewObjectsUiBinder uiBinder = GWT
			.create(ViewObjectsUiBinder.class);
	@UiField
	ScrollPanel centerPanel;
	@UiField
	FlexTable flexyTable;
	@UiField
	Button newObject;
	@UiField
	Button backButton;

	private DatabaseServiceAsync databaseService = GWT
			.create(DatabaseService.class);

	public ViewObjects(HikstComposite parent) {
			initWidget(uiBinder.createAndBindUi(this));
			this.parent = parent;
			initTable();
		}

	ClickHandler createObjectButtonClickHandler = new ClickHandler() {

		@Override
		public void onClick(ClickEvent event) {

		}

	};

	Button createSimObjectButton = new Button("Create object",
			createObjectButtonClickHandler);

	private void initTable() {
		// flexyTable = new FlexTable();

		centerPanel.remove(flexyTable);
		databaseService.getSimObjects(new HikstObjectsCallback(flexyTable,
				parent));
		centerPanel.add(flexyTable);

		// centerPanel.add(flexyTable);
	}

	@UiHandler("newObject")
	void onButtonSave(ClickEvent event){
		RootLayoutPanel.get().add(new NewObject(this));
	}

	@UiHandler("backButton")
	void onBackButtonClick(ClickEvent event) {
		this.removeFromParent();
	}
}
