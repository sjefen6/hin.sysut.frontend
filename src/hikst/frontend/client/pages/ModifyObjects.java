package hikst.frontend.client.pages;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.shared.SimObject;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.Widget;

public class ModifyObjects extends Composite {

	private static ModifyObjectsUiBinder uiBinder = GWT
			.create(ModifyObjectsUiBinder.class);

	interface ModifyObjectsUiBinder extends UiBinder<Widget, ModifyObjects> {
	}

	DatabaseService databaseService = GWT.create(DatabaseServiceAsync.class);

	public ModifyObjects(SimObject simObject) {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
