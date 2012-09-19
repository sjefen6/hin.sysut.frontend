package hikst.frontend.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.TextBox;

public class Setup extends Composite implements HasText {

	private static SetupUiBinder uiBinder = GWT.create(SetupUiBinder.class);
	
	private DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);

	interface SetupUiBinder extends UiBinder<Widget, Setup> {
	}
	
	public Setup() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField TextBox db_hostname;
	@UiField Button install;
	@UiField TextBox db_port;
	@UiField TextBox db_user;
	@UiField TextBox db_password;
	@UiField TextBox db_name;
	
	@UiHandler("install")
	void onButtonClick(ClickEvent e){
		Window.alert("Click!");
		databaseService.setUp(db_hostname.getText(), db_port.getText(), db_name.getText(), db_user.getText(), db_password.getText(),new SetupCallback());
	
	}

	@Override
	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void setText(String text) {
		// TODO Auto-generated method stub
	}





}
