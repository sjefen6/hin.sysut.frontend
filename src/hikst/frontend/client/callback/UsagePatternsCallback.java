package hikst.frontend.client.callback;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.client.DatabaseServiceAsync;
import hikst.frontend.client.pages.NewObject;
import hikst.frontend.client.pages.NewSimulation;
import hikst.frontend.shared.SimObject;
import hikst.frontend.shared.SimObjectTree;
import hikst.frontend.shared.UsagePattern;

import java.util.ArrayList;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;
import com.google.gwt.user.client.ui.RootPanel;

public class UsagePatternsCallback implements AsyncCallback<ArrayList<UsagePattern>>{
	
	private FlexTable usagePatternTable;
	private DatabaseServiceAsync databaseService = GWT.create(DatabaseService.class);
	private Composite parent;
	
	public UsagePatternsCallback(FlexTable usagePatternTable, Composite parent)
	{
		this.usagePatternTable = usagePatternTable;
		this.parent = parent;
	}
	
	@Override
	public void onFailure(Throwable caught) {
		Window.alert("Server failure : " +caught.getMessage());
	}

	@Override
	public void onSuccess(ArrayList<UsagePattern> result) {
		updateTable(result);
	}
	
	private void updateTable(ArrayList<UsagePattern> usagePatterns)
	{
		usagePatternTable.clear();
		
		usagePatternTable.setWidget(0, 0, new Label("Choose UsagePattern"));
		usagePatternTable.setWidget(0, 1, new Label("ID"));
		usagePatternTable.setWidget(0, 2, new Label("Name"));
		usagePatternTable.setWidget(0, 3, new Label("Actual"));
		usagePatternTable.setWidget(0, 4, new Label("Modify"));
		usagePatternTable.setWidget(0, 5, new Label("Duplicate"));
		

		for(int i = 0; i<usagePatterns.size(); i++)
		{
			final UsagePattern usagePattern = usagePatterns.get(i);
			
			int row = i+1;
			usagePatternTable.setWidget(row, 0, new Button("Choose object",
					new ClickHandler()
					{

						@Override
						public void onClick(ClickEvent event) {
							RootLayoutPanel.get().add(new NewObject(parent, usagePattern));
						}
					
					}));
			usagePatternTable.setWidget(row, 1, new Label(String.valueOf(usagePattern.getID())));
			usagePatternTable.setWidget(row, 2, new Label(usagePattern.name));
			usagePatternTable.setWidget(row, 3, new Label(String.valueOf(usagePattern.actual)));
			
			usagePatternTable.setWidget(row, 9, new Button
					(
					"Modify",
					new ClickHandler()
					{
						
						@Override
						public void onClick(ClickEvent event) {
							RootPanel.get().add(new NewUsagePattern(usagePattern));
							
						}
						
					}
					));
			
			usagePatternTable.setWidget(row, 10, new Button
					(
					"Duplicate",
					new ClickHandler()
					{
						@Override
						public void onClick(ClickEvent event) {
							// TODO Auto-generated method stub
							usagePattern.Clone();
							
							SimObjectTree tree = new SimObjectTree();
							tree.rootObject = simObject;
							tree.isEmpty = false;
						}
					}
					));
		}
	}
}
