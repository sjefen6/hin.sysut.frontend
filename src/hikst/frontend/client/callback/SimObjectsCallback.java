package hikst.frontend.client.callback;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.pages.ModifyObjects;
import hikst.frontend.shared.SimObject;
import hikst.frontend.shared.SimObjectTree;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;

public class SimObjectsCallback implements AsyncCallback<ArrayList<SimObject>>{
	
	private FlexTable objectTable;
	private DatabaseService databaseService = GWT.create(DatabaseServiceAsync.class);
	
	public SimObjectsCallback(FlexTable objectTable)
	{
		this.objectTable = objectTable;
	}
	
	@Override
	public void onFailure(Throwable caught) {
	
		Window.alert("Server failure : " +caught.getMessage());
		
	}

	@Override
	public void onSuccess(ArrayList<SimObject> result) {
		
		updateTable(result);
		
	}
	
	private void updateTable(ArrayList<SimObject> simObjects)
	{
		objectTable.clear();
		
		
		
		objectTable.setWidget(0,0,new Label("ID"));
		objectTable.setWidget(0, 1, new Label("Name"));
		objectTable.setWidget(0, 2, new Label("Effect"));
		objectTable.setWidget(0, 3, new Label("Voltage"));
		objectTable.setWidget(0, 4, new Label("Longitude"));
		objectTable.setWidget(0, 5, new Label("Latitude"));
		objectTable.setWidget(0, 6, new Label("Impact Degree ID"));
		objectTable.setWidget(0,7, new Label("Usage Pattern ID"));
		objectTable.setWidget(0, 8, new Label("Modify"));
		objectTable.setWidget(0, 9, new Label("Duplicate"));

		for(int i = 0; i<simObjects.size(); i++)
		{
			final SimObject simObject = simObjects.get(i);
			
			int row = i+1;
			
			objectTable.setWidget(row, 0, new Label(String.valueOf(simObject.getID())));
			objectTable.setWidget(row, 1, new Label(simObject.name));
			objectTable.setWidget(row, 2, new Label(String.valueOf(simObject.effect)));
			objectTable.setWidget(row,3, new Label(String.valueOf(simObject.volt)));
			objectTable.setWidget(row, 4, new Label(String.valueOf(simObject.longitude)));
			objectTable.setWidget(row, 5, new Label(String.valueOf(simObject.latitude)));
			objectTable.setWidget(row, 6, new Label(String.valueOf(simObject.impactDegree)));
			objectTable.setWidget(row, 7, new Label(String.valueOf(simObject.usagePattern)));
			
			objectTable.setWidget(row, 8, new Button
					(
					"Modify",
					new ClickHandler()
					{
						
						@Override
						public void onClick(ClickEvent event) {
							RootPanel.get().add(new ModifyObjects(simObject));
							
						}
						
					}
					));
			objectTable.setWidget(row, 9, new Button
					(
					"Duplicate",
					new ClickHandler()
					{

						@Override
						public void onClick(ClickEvent event) {
							// TODO Auto-generated method stub
							simObject.Clone();
							
							SimObjectTree tree = new SimObjectTree();
							tree.rootObject = simObject;
							tree.isEmpty = false;
							
							databaseService.saveObject(tree);
						}
						
					}
					));
			
		}
	}

}
