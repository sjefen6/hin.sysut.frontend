package hikst.frontend.client.pages;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class SimResults extends Composite {

	
	
	private static SimResultsUiBinder uiBinder = GWT
			.create(SimResultsUiBinder.class);

	interface SimResultsUiBinder extends UiBinder<Widget, SimResults> {
	}

	public SimResults() {
		initWidget(uiBinder.createAndBindUi(this));
	}

}
