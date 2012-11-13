package hikst.frontend.client.callback;

import hikst.frontend.client.pages.HikstComposite;
import hikst.frontend.client.pages.NewObject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class ImpactDegreeCallback implements AsyncCallback<Void>{
	
	private HikstComposite hikstCompositeParent;
	
	public ImpactDegreeCallback(HikstComposite hikstCompositeParent)
	{
		this.hikstCompositeParent = hikstCompositeParent;
	}

	@Override
	public void onFailure(Throwable caught) {
		Window.alert(caught.getMessage());
		
	}

	@Override
	public void onSuccess(Void result) {
		Window.alert("Database insertion successful");
		RootLayoutPanel.get().add(new NewObject((NewObject) hikstCompositeParent));
	}

}
