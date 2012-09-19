package hikst.frontend.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Frontend implements EntryPoint {
	
	public static Integer loadable;
	
	public Frontend(){
		loadable = -1;
		
	}
	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network "
			+ "connection and try again.";
	
	
	private String message = "";
	MyDockLayoutPanel panel;
	Login panel2;
	Setup panelSetup;
	Widget maps;
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
//		Window.alert(String.valueOf(loadable));
//		//RootLayoutPanel.get().add(new Login());
//		
//		/*while (loadable == -1){
//			try {
//				Thread.sleep(5);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			//this.onModuleLoad();
//		} */
//		
//		if (loadable == -1) {
//			Timer togglebuttonTimer = new Timer() { 
//			    public void run() { 
//			    	onModuleLoad(); 
//			    } 
//			}; 
//			togglebuttonTimer.schedule(5);
//		}
//		else if (loadable == 1){
//			RootLayoutPanel.get().add(new Login());
//
//		} else if (loadable == 0) {
//			RootLayoutPanel.get().add(new Setup());
//		} else {
//			//Simply wtf!!
//			//System.exit(0);
		
		RootLayoutPanel.get().add(new Start());
		}
		
//		databaseService.settingsLoadable(new SettingsLoadableCallback(new Login(), new Setup()));
	
}
