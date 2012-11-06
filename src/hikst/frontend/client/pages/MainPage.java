package hikst.frontend.client.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlowPanel;
 

public class MainPage extends Composite implements HasText {



	private static MainPageUiBinder uiBinder = GWT
			.create(MainPageUiBinder.class);
	@UiField Button buttonLogout;
	@UiField Button emailAdmin;
	@UiField Button adminAccount;
	@UiField FlowPanel centerPanel;
	@UiField Button viewSimsButton;

	interface MainPageUiBinder extends UiBinder<Widget, MainPage> {
	}

	public MainPage() {
		initWidget(uiBinder.createAndBindUi(this));

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

	@UiHandler("newSim")
	void onNewSimClick(ClickEvent event) {
		RootLayoutPanel.get().add(new NewSimulation());
	}
	
	@UiHandler("viewSimsButton")
	void onViewSimsButtonClick(ClickEvent event){
		RootLayoutPanel.get().add(new ViewSimulations());
	}
	@UiHandler("emailAdmin")
	void onEmailAdminClick(ClickEvent event) {
		
		//MailCallback mail = new 
		//MyServiceAsync svc = (MyServiceAsync) GWT.create(MyService.class);
        //ServiceDefTarget endpoint = (ServiceDefTarget) svc;
        //endpoint.setServiceEntryPoint("/myService");
       // svc.myMethod("Do Stuff", callback);
		RootLayoutPanel.get().add(new EmailAdmin());
	}
	

	
	
	@UiHandler("buttonLogout")
	void onButtonLogoutClick(ClickEvent event) {
		RootLayoutPanel.get().add(new Login());
	}
}
