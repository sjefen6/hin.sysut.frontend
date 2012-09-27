package hikst.frontend.client.pages;

import hikst.frontend.client.SplineGraf;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.client.ui.FlowPanel;

public class ObjectMenu extends Composite implements HasText {

	ViewObjects panel;
	MainPage panelBack;
	private static ObjectMenuUiBinder uiBinder = GWT
			.create(ObjectMenuUiBinder.class);
	@UiField Button back;
	@UiField Button addObject;
	@UiField DateBox fromDate;
	@UiField DateBox toDate;
	@UiField Button buttonShowSpline;
	@UiField FlowPanel eastPanel;
	@UiField FlowPanel centerPanel;
	

	interface ObjectMenuUiBinder extends UiBinder<Widget, ObjectMenu> {
	}

	public ObjectMenu() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("addObject")
	void onAddObjectClick(ClickEvent event) {
		//RootLayoutPanel.get().add(new NewObject());
		RootLayoutPanel.get().add(new ViewObjects());
		panel = new ViewObjects();
		RootLayoutPanel.get().add(panel);
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
	@UiHandler("back")
	void onBackClick(ClickEvent event) {
		//RootLayoutPanel.get().add(new MainPage());
		panelBack = new MainPage();
		RootLayoutPanel.get().add(panelBack);
	}


	@UiHandler("buttonShowSpline")
	void onButtonShowSplineClick(ClickEvent event) {
		centerPanel.add(SplineGraf.createChart());
		System.out.println("Should show spline!!!");
	}
}