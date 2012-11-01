package hikst.frontend.client.pages;

import hikst.frontend.client.MyService;
import hikst.frontend.client.MyServiceAsync;

import java.util.Date;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasAlignment;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlowPanel;


import org.moxieapps.gwt.highcharts.client.*;  
import org.moxieapps.gwt.highcharts.client.labels.*;  
import org.moxieapps.gwt.highcharts.client.plotOptions.*;
 

public class MainPage extends Composite implements HasText {

	NewSimulation panel;
	MyDockLayoutPanel oldPanel;
	EmailAdmin mailPanel;
	SimResults simPanel;
	private static MainPageUiBinder uiBinder = GWT
			.create(MainPageUiBinder.class);
	@UiField Button buttonLogout;
	@UiField Button emailAdmin;
	@UiField Button adminAccount;
	@UiField Button oldFront;
	@UiField FlowPanel centerPanel;
	@UiField Button simResoult;

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
		panel = new NewSimulation();
		RootLayoutPanel.get().add(panel);
	}
	@UiHandler("oldFront")
	void onOldFrontClick(ClickEvent event) {
		RootLayoutPanel.get().add(new MyDockLayoutPanel());
		oldPanel = new MyDockLayoutPanel();
		RootLayoutPanel.get().add(oldPanel);	
	}

	
	
	@UiHandler("simResoult")
	void onSimResoultClick(ClickEvent event){
		simPanel = new SimResults();
		RootLayoutPanel.get().add(simPanel);
	}
	@UiHandler("emailAdmin")
	void onEmailAdminClick(ClickEvent event) {
		
		//MailCallback mail = new 
		//MyServiceAsync svc = (MyServiceAsync) GWT.create(MyService.class);
        //ServiceDefTarget endpoint = (ServiceDefTarget) svc;
        //endpoint.setServiceEntryPoint("/myService");
       // svc.myMethod("Do Stuff", callback);
		mailPanel = new EmailAdmin();
		RootLayoutPanel.get().add(mailPanel);
	}
	

	
	
}
