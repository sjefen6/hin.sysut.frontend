package hikst.frontend.client;

import hikst.frontend.shared.Description;
import hikst.frontend.shared.Plot;
import hikst.frontend.shared.SimulatorObject;
import hikst.frontend.shared.LoginRequest;
import hikst.frontend.shared.RegisterRequest;
import hikst.frontend.shared.SimulationRequest;
import hikst.frontend.shared.SimulationTicket;

import java.util.List;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("database")
public interface DatabaseService extends RemoteService
{
	//for getting simulation data
	List<Integer> getSimulationDescriptionsIDs() throws IllegalArgumentException;
	List<Plot> getData(int sim_description_id) throws IllegalArgumentException;
	SimulatorObject getSimulatorObject(int simulation_object_id) throws IllegalArgumentException;
	Description getSimulation(int sim_description_id) throws IllegalArgumentException;
	List<Description> getSimulations() throws IllegalArgumentException;
	
	//User authentication management
	boolean authenticate(LoginRequest request);
	boolean logOff();
	boolean register(RegisterRequest request);
	boolean exists(String username);
	boolean changeAccessLevel(String username,String access_level);

	//simulation management
	int saveObject(SimulatorObject object);
	boolean updateObject(int id,SimulatorObject object);
	boolean deleteObject(int object_id);
	SimulationTicket requestSimulation(SimulationRequest request);
	boolean deleteSimulations(int id);
	int getSimulationStatus(SimulationTicket ticket);
	
	 
}
