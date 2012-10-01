package hikst.frontend.client;

import hikst.frontend.client.pages.Start;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Frontend implements EntryPoint {
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		// Hadde tr�bbel med � gj�re callbacks i Enterypoint. Sender brukeren til Start s� vi kan gj�re callbacks
		RootLayoutPanel.get().add(new Start());
		}
}
