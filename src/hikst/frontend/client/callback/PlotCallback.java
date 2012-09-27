package hikst.frontend.client.callback;

import hikst.frontend.client.pages.MyDockLayoutPanel;
import hikst.frontend.shared.Plot;

import java.util.List;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class PlotCallback implements AsyncCallback<List<Plot>>
{
	MyDockLayoutPanel panel;
	
	PlotCallback(MyDockLayoutPanel panel)
	{
		this.panel = panel;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		
		Window.alert(caught.getMessage());
		
	}

	@Override
	public void onSuccess(List<Plot> result) {
		
		//panel.setData();
	}

}
