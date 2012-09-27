package hikst.frontend.client.pages;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.Graph;
import hikst.frontend.client.Simulation;
import hikst.frontend.client.callback.LoginCallback;
import hikst.frontend.shared.Description;
import hikst.frontend.shared.LoginRequest;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class Login extends Composite implements HasText {

	private final String COOKIE_NAME = "HIKST";

	private String user1 = "Navn";
	private String userpass = "Passord";
	Simulation simulation;

	private DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);

	private static LoginUiBinder uiBinder = GWT.create(LoginUiBinder.class);
	MainPage panel;
	interface LoginUiBinder extends UiBinder<Widget, Login> {
	}

	public Login() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField Button button;
	@UiField TextBox user;

	@UiHandler("user")
	void onClick1(ClickEvent event){
		user.setText("");
	}

	public Login(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
		button.setText(firstName);
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		RootLayoutPanel.get().add(new MainPage());
		panel = new MainPage();

		LoginRequest request = new LoginRequest(user1, userpass);

		databaseService.authenticate(request, new LoginCallback(this));


	}

	public void GoToMainPage()
	{
		RootLayoutPanel.get().add(panel);
	}

	public void setText(String text) {
		button.setText(text);
	}

	public String getText() {
		return button.getText();
	}
    
	public void setData(Description description)
    {
    	simulation = new Simulation(description);
    	Graph powerGraph = simulation.getEffectGraph();
		powerGraph.update();
    }
}