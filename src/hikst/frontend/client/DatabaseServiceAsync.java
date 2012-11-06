package hikst.frontend.client;

import hikst.frontend.shared.Description;
import hikst.frontend.shared.HikstObject;
import hikst.frontend.shared.HikstObjectTree;
import hikst.frontend.shared.ImpactType;
import hikst.frontend.shared.LoginRequest;
import hikst.frontend.shared.Plot;
import hikst.frontend.shared.RegisterRequest;
import hikst.frontend.shared.SimObjectTree;
import hikst.frontend.shared.SimulationRequest;
import hikst.frontend.shared.SimulationTicket;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;




/**
 * The async counterpart of <code>DatabaseService</code>.
 */
public interface DatabaseServiceAsync {

	void getData(int sim_description_id, AsyncCallback<List<Plot>> callback)
	throws IllegalArgumentException;
	void getSimulatorObject(int simulation_object_id,
			AsyncCallback<SimObjectTree> callback);
	void getSimulationDescriptionsIDs(AsyncCallback<List<Integer>> callback)
	throws IllegalArgumentException;
	void getSimulation(int sim_description_id, AsyncCallback<Description> callback)
	throws IllegalArgumentException;
	void getSimulations(AsyncCallback<List<Description>> callback)
	throws IllegalArgumentException;
	void authenticate(LoginRequest request, AsyncCallback<Boolean> callback) 
	throws IllegalArgumentException;
	void logOff(AsyncCallback<Boolean> callback)
	throws IllegalArgumentException;
	void register(RegisterRequest request, AsyncCallback<Boolean> callback)
	throws IllegalArgumentException;
	void exists(String username, AsyncCallback<Boolean> callback)
	throws IllegalArgumentException;
	void requestSimulation(SimulationRequest request,
			AsyncCallback<Integer> callback);
	void deleteObject(int object_id, AsyncCallback<Boolean> callback)
	throws IllegalArgumentException;
	void deleteSimulations(int id, AsyncCallback<Boolean> callback)
	throws IllegalArgumentException;
	//void updateObject(int id ,SimulatorObject object, AsyncCallback<Boolean> callback)
	//throws IllegalArgumentException;
	void changeAccessLevel(String username, String access_level,
			AsyncCallback<Boolean> callback)
	throws IllegalArgumentException;
	void getSimulationStatus(SimulationTicket ticket,
			AsyncCallback<Integer> callback);
	void setUp(String hostname, String port, String name, String user,
			String password, AsyncCallback<Boolean> callback);
	void settingsLoadable(AsyncCallback<Boolean> callback);
	void loadObject(int id, AsyncCallback<HikstObjectTree> callback);
	void getSimObjects(AsyncCallback<ArrayList<HikstObject>> callback);
	void saveObject(HikstObject simObject, AsyncCallback<Integer> callback);
	void getImpactTypes(AsyncCallback<ArrayList<ImpactType>> callback);
	void addImpactDegree(double percent, int object_id, int type_id,
			AsyncCallback<Void> callback);
}
