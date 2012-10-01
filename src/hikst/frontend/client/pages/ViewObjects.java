package hikst.frontend.client.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasText;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ViewObjects extends Composite {

	VerticalPanel panel = new VerticalPanel();
	FlexTable objectTable;
	
	ClickHandler createObjectButtonClickHandler = new ClickHandler()
	{

		@Override
		public void onClick(ClickEvent event) {
			
			//RootPanel.get().add(new )
			// TODO: Skrive ferdig her..
			
		}
		
	};
	
	Button createSimObjectButton = new Button("Create object",createObjectButtonClickHandler);
	
	public ViewObjects() {
		//initWidget(uiBinder.createAndBindUi(this));
		initWidget(panel);
		initButtons();
		initTable();
	}
	
	private void initTable()
	{
		objectTable = new FlexTable();
		
		
		
		panel.add(objectTable);
	}
	
	private void initButtons()
	{
		HorizontalPanel buttonPanel = new HorizontalPanel();
		buttonPanel.add(createSimObjectButton);

		panel.add(buttonPanel);
	}


}
