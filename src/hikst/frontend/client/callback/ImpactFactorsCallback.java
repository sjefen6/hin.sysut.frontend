package hikst.frontend.client.callback;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.shared.ImpactType;
import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
	
	public class ImpactFactorsCallback implements AsyncCallback<ArrayList<ImpactType>> {

 
		
		
		private ListBox ImpactTypeBox;
		private DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);
		private Composite parent;
		
		public ImpactFactorsCallback(ListBox ImpactTypeBox, Composite parent)
		{
			this.ImpactTypeBox = ImpactTypeBox;
			this.parent = parent;
		}
		
		@Override
		public void onFailure(Throwable caught) {
		
			Window.alert("Server failure : " +caught.getMessage());
			
		}

		@Override
		public void onSuccess(ArrayList<ImpactType> result) {
			
			updateTable(result);
			
		}
		
		private void updateTable(ArrayList<ImpactType> types)
		{
			ImpactTypeBox.clear();
			for (ImpactType t: types){
				
			ImpactTypeBox.addItem(t.name, Integer.toString(t.ID));
			}
		}
}
