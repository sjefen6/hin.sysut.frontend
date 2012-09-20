package hikst.frontend.client;

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

public class MainPage extends Composite implements HasText {

	ObjectMenu panel;
	MyDockLayoutPanel oldPanel;
	
	private static MainPageUiBinder uiBinder = GWT
			.create(MainPageUiBinder.class);
	@UiField Button buttonLogout;
	@UiField Button emailAdmin;
	@UiField Button adminAccount;
	@UiField Button oldFront;

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
		RootLayoutPanel.get().add(new ObjectMenu());
		panel = new ObjectMenu();
		RootLayoutPanel.get().add(panel);
	}
	@UiHandler("oldFront")
	void onOldFrontClick(ClickEvent event) {
		RootLayoutPanel.get().add(new MyDockLayoutPanel());
		oldPanel = new MyDockLayoutPanel();
		RootLayoutPanel.get().add(oldPanel);
		
		
	}
}
