package hikst.frontend.client;

import hikst.frontend.shared.Description;
import hikst.frontend.shared.HikstObject;
import hikst.frontend.shared.HikstObjectTree;
import hikst.frontend.shared.ImpactType;
import hikst.frontend.shared.Plot;
import hikst.frontend.shared.SimObjectTree;
import hikst.frontend.shared.LoginRequest;
import hikst.frontend.shared.RegisterRequest;
import hikst.frontend.shared.SimulationRequest;
import hikst.frontend.shared.SimulationTicket;
import hikst.frontend.shared.ViewSimulationObject;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("database")
public interface DatabaseService extends RemoteService
{
	//for getting simulation data
	List<Integer> getSimulationDescriptionsIDs() throws IllegalArgumentException;
	List<Plot> getData(int sim_description_id) throws IllegalArgumentException;
	SimObjectTree getSimulatorObject(int simulation_object_id) throws IllegalArgumentException;
	Description getSimulation(int sim_description_id) throws IllegalArgumentException;
	List<Description> getSimulations() throws IllegalArgumentException;
	
	//db setup
	boolean setUp(String hostname, String port, String name, String user, String password);
	boolean settingsLoadable();
	
	//User authentication management
	boolean authenticate(LoginRequest request);
	boolean logOff();
	boolean register(RegisterRequest request);
	boolean exists(String username);
	boolean changeAccessLevel(String username,String access_level);

	//boolean updateObject(int id,SimulatorObject object);
	boolean deleteObject(int object_id);
	Integer requestSimulation(SimulationRequest request);
	boolean deleteSimulations(int id);
	int getSimulationStatus(SimulationTicket ticket);
	HikstObjectTree loadObject(int id);
	ArrayList<HikstObject> getSimObjects();
	int saveObject(HikstObject simObject);
	public ArrayList<ImpactType> getImpactTypes();
	public ArrayList<ViewSimulationObject> getViewSimulationObjects();
}
