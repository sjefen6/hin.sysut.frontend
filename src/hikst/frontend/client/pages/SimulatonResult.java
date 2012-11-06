package hikst.frontend.client.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SimulatonResult extends Composite {

	private static SimulatonResultUiBinder uiBinder = GWT
			.create(SimulatonResultUiBinder.class);

	interface SimulatonResultUiBinder extends UiBinder<Widget, SimulatonResult> {
	}

	public SimulatonResult() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiField
	Button backButton;

	public SimulatonResult(String firstName) {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("backButton")
	void onClick(ClickEvent e) {

	}
}
