package hikst.frontend.client.callback;

import java.util.ArrayList;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.shared.ImpactType;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.FlexTable;

public class SimulationsListCallback implements AsyncCallback<ImpactType> {

	private FlexTable SimulationsTable;

	private DatabaseServiceAsync databaseService = GWT
			.create(DatabaseService.class);

	public SimulationsListCallback() {
	}

	@Override
	public void onFailure(Throwable caught) {

		Window.alert("Server failure : " + caught.getMessage());

	}

	@Override
	public void onSuccess(ImpactType result) {
		// TODO Auto-generated method stub
		
		// updateTable(result);
	}
	
	private void updateTable(ArrayList<ImpactType> types) {
		SimulationsTable.clear();
		for (ImpactType t : types) {

			//SimulationsTable.addItem(t.name, Integer.toString(t.ID));
		}


}
