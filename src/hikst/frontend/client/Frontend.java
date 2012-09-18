package hikst.frontend.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootLayoutPanel;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Frontend implements EntryPoint {
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	
	private DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);
	private String message = "";
	MyDockLayoutPanel panel;
	Login panel2;
	Setup panelSetup;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		if (Settings.loadable()){
		// RootLayoutPanel.get().add(new MyDockLayoutPanel());
		// panel = new MyDockLayoutPanel();
			//makeCall();
			RootLayoutPanel.get().add(new Login());
			panel2 = new Login();
			RootLayoutPanel.get().add(panel2);
		} else {
			RootLayoutPanel.get().add(new Setup());
			panelSetup = new Setup();
			RootLayoutPanel.get().add(panelSetup);
		}
	}
	private void makeCall()
	{	
		databaseService.getSimulations(new ListDescriptionsCallback(panel));	
	}
}
