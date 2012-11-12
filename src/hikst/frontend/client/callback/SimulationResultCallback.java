package hikst.frontend.client.callback;

import java.util.ArrayList;
import java.util.List;

import hikst.frontend.client.pages.MyDockLayoutPanel;
import hikst.frontend.client.pages.SimulatonResult;
import hikst.frontend.shared.Description;
import hikst.frontend.shared.Plot;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class SimulationResultCallback implements AsyncCallback<Description>
{
	SimulatonResult panel;
	
	public SimulationResultCallback(SimulatonResult panel)
	{
		this.panel = panel;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		
		Window.alert(caught.getMessage());
	}

	@Override
	public void onSuccess(Description result) {
		
		List<Plot> plots = result.getPlots();
		int currentSize = panel.graf.size();
		if(plots.size() > currentSize)
		{	
			for(int index =currentSize; index < plots.size(); index++)
			{
				Plot plot = plots.get(index);
				panel.graf.addPoint((double)plot.getEffectY(), plot.getDateX().getTime());
			}
		}
	}

}
