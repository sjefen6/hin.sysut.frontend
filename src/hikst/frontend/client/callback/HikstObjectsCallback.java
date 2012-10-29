package hikst.frontend.client.callback;

import hikst.frontend.client.pages.HikstComposite;
import hikst.frontend.client.pages.NewObject;
import hikst.frontend.client.pages.NewSimulation;
import hikst.frontend.shared.HikstObject;
import java.util.ArrayList;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootLayoutPanel;

public class HikstObjectsCallback implements
		AsyncCallback<ArrayList<HikstObject>> {

	private FlexTable objectTable;
//	private DatabaseServiceAsync databaseService = GWT
//			.create(DatabaseService.class);
	private HikstComposite hikstCompositeParent;

	public HikstObjectsCallback(FlexTable objectTable, HikstComposite hikstCompositeParent) {
		this.objectTable = objectTable;
		this.hikstCompositeParent = hikstCompositeParent;
	}

	@Override
	public void onFailure(Throwable caught) {

		Window.alert("Server failure : " + caught.getMessage());

	}

	@Override
	public void onSuccess(ArrayList<HikstObject> result) {

		updateTable(result);

	}

	private void updateTable(ArrayList<HikstObject> simObjects) {
		objectTable.clear();

		objectTable.setWidget(0, 0, new Label("Choose object"));
		objectTable.setWidget(0, 2, new Label("Name"));
		objectTable.setWidget(0, 9, new Label("Modify"));
		objectTable.setWidget(0, 10, new Label("Duplicate"));

		for (int i = 0; i < simObjects.size(); i++) {
			final HikstObject simObject = simObjects.get(i);

			int row = i + 1;
			objectTable.setWidget(row, 0, new Button("Choose object",
					new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {

							if (hikstCompositeParent instanceof NewObject) {

								RootLayoutPanel.get().add(
										new NewObject(hikstCompositeParent, simObject));
							} else if (hikstCompositeParent instanceof NewSimulation) {
								RootLayoutPanel.get().add(
										new NewSimulation(hikstCompositeParent, simObject));
							}

						}

					}));
			objectTable.setWidget(row, 1,
					new Label(String.valueOf(simObject.getID())));
			objectTable.setWidget(row, 2, new Label(simObject.name));

			objectTable.setWidget(row, 3, new Button("Modify",
					new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							// RootLayoutPanel.get().add(new
							// NewObject(simObject));

						}

					}));
			objectTable.setWidget(row, 4, new Button("Duplicate",
					new ClickHandler() {

						@Override
						public void onClick(ClickEvent event) {
							// TODO Auto-generated method stub
							// simObject.Clone();
							//
							// SimObjectTree tree = new SimObjectTree();
							// tree.rootObject = simObject;
							// tree.isEmpty = false;

						}

					}));
		}
	}
}