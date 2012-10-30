package hikst.frontend.client.pages;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.callback.SettingsLoadableCallback;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;

public class Start extends Composite {

	private static StartUiBinder uiBinder = GWT.create(StartUiBinder.class);
	private DatabaseServiceAsync databaseService = GWT
			.create(DatabaseService.class);

	interface StartUiBinder extends UiBinder<Widget, Start> {
	}

	public Start() {
		initWidget(uiBinder.createAndBindUi(this));
		databaseService.settingsLoadable(new SettingsLoadableCallback());
	}

	public static void go(boolean loadable) {
		if (loadable) {
			RootLayoutPanel.get().add(new Login());

		} else {
			RootLayoutPanel.get().add(new Setup());
		}
	}

	public Start(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}
}
