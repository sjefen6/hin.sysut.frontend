package hikst.frontend.client.pages;

import java.util.Date;

import hikst.frontend.client.SplineGraph;
import hikst.frontend.shared.Description;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.FlowPanel;


import org.moxieapps.gwt.highcharts.client.*;  
import org.moxieapps.gwt.highcharts.client.labels.*;  
import org.moxieapps.gwt.highcharts.client.plotOptions.*;
 

public class MainPage extends Composite implements HasText {

	NewSimulation panel;
	MyDockLayoutPanel oldPanel;
	SimResoults simPanel;
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
		simPanel = new SimResoults();
		RootLayoutPanel.get().add(simPanel);
	}
}
