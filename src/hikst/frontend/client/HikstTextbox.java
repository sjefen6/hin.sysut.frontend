package hikst.frontend.client;

import com.google.gwt.user.client.ui.TextBox;

public class HikstTextbox extends TextBox {
	public HikstTextbox(final String placeHolder) {
		super();
		getElement().setAttribute("placeHolder", placeHolder);
	}
}
