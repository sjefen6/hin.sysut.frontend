package hikst.frontend.server;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpSession;

import hikst.frontend.client.DatabaseService;
import hikst.frontend.shared.Description;
import hikst.frontend.shared.HikstObject;
import hikst.frontend.shared.LoginRequest;
import hikst.frontend.shared.Plot;
import hikst.frontend.shared.RegisterRequest;
import hikst.frontend.shared.SimObject;
import hikst.frontend.shared.SimObjectTree;
import hikst.frontend.shared.SimulationRequest;
import hikst.frontend.shared.SimulationTicket;
import hikst.frontend.shared.SimulatorObject;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class DatabaseServiceImpl extends RemoteServiceServlet implements DatabaseService
{	
	private static final int SALT_LENGTH = 20;
	private static final SecureRandom randomizer = new SecureRandom();
	
	@Override
	public List<Plot> getData(int sim_description_id) throws IllegalArgumentException {
		
		Connection connection = Settings.getDBC();
		List<Plot> plots = new ArrayList<Plot>();
		
		try
		{
			String query = "SELECT Time, Effect, Power_Consumption, Voltage, Current FROM Simulations WHERE Sim_Description_ID=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, sim_description_id);
			ResultSet set = statement.executeQuery();
			
			while(set.next())
			{
				Date time = new Date(set.getLong(1));
				float effect = set.getFloat(2);
				float power_consumption = set.getFloat(3);
				float voltage = set.getFloat(4);
				float current = set.getFloat(5);
				
				plots.add(new Plot(time,effect,power_consumption,voltage,current));
			}
		}
		catch(SQLException ex)
		{
			//send out error message some way....
			ex.printStackTrace();
		}
	
		return plots;
	}

	@Override
	public SimObjectTree getSimulatorObject(int simulation_object_id)throws IllegalArgumentException
	{
		Connection connection = Settings.getDBC();
		
		try
		{		
			
			String query = "SELECT Name FROM Objects WHERE ID=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, simulation_object_id);
			ResultSet set = statement.executeQuery();
			
			if(set.next())
			{
				String name = set.getString(1);
				SimObjectTree simObjekt = new SimObjectTree();
			}
			else
			{
				throw new IllegalArgumentException();
			}
			
		}catch(SQLException ex)
		{
			ex.printStackTrace();
			
		}
	
		return null;
	}

	@Override
	public List<Integer> getSimulationDescriptionsIDs(){
		
		List<Integer> simulation_descriptions = new ArrayList<Integer>();
		
		Connection connection = Settings.getDBC();
		
		try
		{
			
			String query = "SELECT Simulation_Descriptions.ID FROM Simulation_Descriptions, Simulator_Queue_Objects WHERE Simulation_Descriptions.ID = Simulator_Queue_Objects.Simulation_Descriptions_ID and Status_ID=4";
			PreparedStatement statement = connection.prepareStatement(query);
			ResultSet set = statement.executeQuery();
			
			while(set.next())
			{
				simulation_descriptions.add(set.getInt(1));
			}	
		}
		catch(SQLException ex)
		{
			//send message back to client in some way...
			//paste in here...
			ex.printStackTrace();
			throw new IllegalArgumentException();
		}
		
		return simulation_descriptions;
	}
	
	@Override
	public Description getSimulation(int sim_description_id)
	{
		try
		{
			Connection connection = Settings.getDBC();
			
			String query = "SELECT Object_ID, minimumTime, maximumTime FROM Simulation_Descriptions WHERE ID=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, sim_description_id);
			ResultSet set = statement.executeQuery();
			
			if(set.next())
			{
				int object_id = set.getInt(1);
				long minimumTime = set.getLong(2);
				long maximumTime = set.getLong(3);
				
				//SimulatorObject simulator_object = getSimulatorObject(object_id);
				//List<Plot> plots = getData(sim_description_id);
				
				//return new Description(simulator_object,plots,minimumTime,maximumTime);
			}
			else
			{
				throw new IllegalArgumentException();
			}
			
		}catch(SQLException ex)
		{
			//send message back to server that the request failed
			ex.printStackTrace();
			throw new IllegalArgumentException();
			
		}
		
		return null;
	}

	@Override
	public List<Description> getSimulations() throws IllegalArgumentException {
		
		List<Description> simulations = new ArrayList<Description>();
		
		List<Integer> descriptionIDs = getSimulationDescriptionsIDs();
		
		for(int i = 0; i<descriptionIDs.size(); i++)
		{
			simulations.add(this.getSimulation(descriptionIDs.get(i)));
		}
		
		return simulations;
	}

	@Override
	public boolean authenticate(LoginRequest request) {
		
		
		//return true;
		String username = request.getUsername();
		String password = request.getPassword();
		System.out.println(password);
		Connection connection = Settings.getDBC();
		
		try
		{
			String query = "SELECT Firstname, Lastname, Email, Password, Salt, Access_Level_ID FROM Users WHERE Username=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, username);
			ResultSet set = statement.executeQuery();
			
			if(set.next())
			{
				String firstname = set.getString(1);
				String lastname = set.getString(2);
				String email = set.getString(3);
				String passwordFromDB = set.getString(4);
				String salt = set.getString(5);
				int access_level_id = set.getInt(6);
				
				
				String access_level = String.valueOf(access_level_id);
				
				
				try
				{
					String hashedPassword = getHash(password);
					System.out.println(hashedPassword);
					//log-in attempt successful!!
					if(passwordFromDB.equals(hashedPassword))
					{
						HttpSession session = getSession();
						
						User user = new User(username,firstname,lastname,email,salt, access_level);
						session.setAttribute(hashedPassword, user);
						
						return true;
					}
					else
					{
						return false;
					}	
				}
				catch(NoSuchAlgorithmException ex)
				{
					ex.printStackTrace();
					return false;
				}
				catch(UnsupportedEncodingException ex)
				{
					ex.printStackTrace();
					return false;
				}
			}
			else
			{
				return false;
			}
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
		/*catch(AccessLevelNotFoundException ex)
		{
			ex.printStackTrace();
			return false;
		}
		*/
		
	}
	
	private String getHash(String key) throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
			MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
			/*
			String text = key;
			messageDigest.update(text.getBytes("UTF-8"));
			byte[] hash = messageDigest.digest();
			return new String(hash);
			*/
			messageDigest.update(key.getBytes());
			return new sun.misc.BASE64Encoder().encode(messageDigest.digest());
			
	}
	
	private String generateSalt()
	{
		byte[] salt = new byte[SALT_LENGTH];
		randomizer.nextBytes(salt);
		return new String(salt);
	}
	
	private HttpSession getSession()
	{
		return this.getThreadLocalRequest().getSession();
	}

	@Override
	public boolean logOff() {
		
		HttpSession session = getSession();
		session.invalidate();
		return true;
	}

	@Override
	public boolean register(RegisterRequest request) 
	{	
		Connection connection = Settings.getDBC();
		
		try
		{
			String query = "INSERT INTO Users VALUES(?,?,?,?,?,?,?);";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, request.getUsername());
			statement.setString(2, request.getFirstname());
			statement.setString(3, request.getLastname());
			statement.setString(4, request.getEmail());
			
			String salt = generateSalt();
			String hashedPassword = getHash(request.getPassword());
			statement.setString(5, hashedPassword);
			statement.setString(6,salt);
			//sets to 0 for regular user-access
			statement.setInt(7, 0);
			
			statement.executeQuery();
			
			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
		catch(NoSuchAlgorithmException ex)
		{
			ex.printStackTrace();
			return false;
		}
		catch(UnsupportedEncodingException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean exists(String username) {
	
		Connection connection = Settings.getDBC();
		
		try
		{
			String query = "SELECT Username FROM Users WHERE Username=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1,username);
			ResultSet set = statement.executeQuery();
			
			return set.next();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

//	private Integer saveImpactDegree(ImpactDegree impactDegree)
//	{
//		if(impactDegree == null)
//			return null;
//		
//		try
//		{
//			Connection connection = Settings.getDBC();
//			
//			String query = "INSERT INTO Impact_Degrees(Percent, Type_ID) VALUES(?,?);";
//			PreparedStatement statement = connection.prepareStatement(query);
//			statement.setDouble(1, impactDegree.getPercent());
//			statement.setInt(2, impactDegree.getTypeID());
//			
//			return statement.executeUpdate();
//		}
//		catch(SQLException ex)
//		{
//			ex.printStackTrace();
//			return null;
//		}
//	}
	
	private int saveSimObject(SimObject simObject) throws SQLException
	{
		Connection connection = Settings.getDBC();
		String query = "INSERT INTO Objects(Name,Effect,Voltage,Current, Longitude, Latitude) VALUES(?,?,?,?,?,?) RETURNING ID";
		PreparedStatement statement;
		
		statement = connection.prepareStatement(query);
		
		statement.setString(1, simObject.name);
		statement.setDouble(2, simObject.effect);
		statement.setDouble(3, simObject.volt);
		statement.setDouble(4, simObject.volt);
		statement.setInt(5, simObject.longitude);
		statement.setInt(6, simObject.latitude);
		//statement.setFloat(5, simObject.impactDegree);
		ResultSet set = statement.executeQuery();
		set.next();
		int object_id = set.getInt(1);
		
		Iterator<SimObject> children = simObject.getChildIterator();
		
		while(children.hasNext())
		{
			SimObject child = children.next();
			int child_id = child.getID();
			
			if(child.isFromDatabase())
			{
				updateSimObject(child);
			}
			else
			{
				 child_id = saveSimObject(child);
			}
			
			saveChildLink(object_id,child_id);
		}
		
		return object_id;
	}
	
	private SimObject getSimObject(int id) throws SQLException
	{
		Connection connection = Settings.getDBC();
		String query = "SELECT Name, Effect, Voltage, Current, Longitude, Latitude, self_temperature, target_temperature, base_area, base_height WHERE ID=?";
		PreparedStatement statement = connection.prepareStatement(query);
		statement.setInt(1, id);
		
		ResultSet set = statement.executeQuery();
		
		if(set.next()){
		SimObject simObject = new SimObject(id);
		simObject.name = set.getString(1);
		simObject.effect = set.getFloat(2);
		simObject.volt = set.getFloat(3);
		//skal egentlig være strøm / ampere
		simObject.volt = set.getFloat(4);
		simObject.impactDegree = set.getFloat(5);
		simObject.longitude = set.getInt(6);
		simObject.latitude = set.getInt(7);
		simObject.self_temperature = set.getInt(8);
		simObject.target_temperature = set.getInt(9);
		simObject.base_area = set.getInt(10);
		simObject.base_height = set.getInt(11);
		return simObject;
		}else
		{
			return null;
		}
		
		
	}
	
	private void updateSimObject(SimObject simObject) throws SQLException
	{
		Connection connection = Settings.getDBC();
		String query = "UPDATE Objects SET Name=?,Effect=?,Voltage=?,Current=?, Longitude=?, Latitude=?, self_temperature, target_temperature, base_area" +
				" base_height,  WHERE ID=?";
		PreparedStatement statement;
		
		statement = connection.prepareStatement(query);
		
		int objectID = simObject.getID();
		statement.setString(1, simObject.name);
		statement.setDouble(2, simObject.effect);
		statement.setDouble(3, simObject.volt);
		statement.setDouble(4, simObject.volt);
		statement.setFloat(5, simObject.impactDegree);
		statement.setInt(6, simObject.longitude);
		statement.setInt(7, simObject.latitude);
		statement.setDouble(8, simObject.self_temperature);
		statement.setDouble(9, simObject.target_temperature);
		statement.setDouble(10, simObject.base_area);
		statement.setDouble(11, simObject.base_height);
		statement.executeUpdate();// TODO: PÅL!
		
		Iterator<SimObject> children = simObject.getChildIterator();
		
		while(children.hasNext())
		{
			SimObject child = children.next();
			int child_id = child.getID();
			
			if(child.isFromDatabase())
			{
				updateSimObject(child);
			}
			else
			{
				child_id = saveSimObject(child);
			}
			

			saveChildLink(objectID,child_id);
		}
	}
	
	private void saveChildLink(int object_id, int child_id)throws SQLException
	{
		Connection connection = Settings.getDBC();
		
		String anotherQuery = "INSERT INTO Part_Objects(Father_ID,Son_ID) VALUES(?,?)";
		PreparedStatement anotherStatement = connection.prepareStatement(anotherQuery);
		anotherStatement.setInt(1, object_id);
		anotherStatement.setInt(2, child_id);
		anotherStatement.executeUpdate();
	}
	
	//saves the simulator object in the database and returns the ID in the database
	@Override
	public int saveObject(SimObjectTree simulatorObject) {
		
		try
		{
			if(simulatorObject.rootObject.isFromDatabase()){
				//returns the id of the object if object saved
				updateSimObject(simulatorObject.rootObject);
			}
			else
			{
				
				return saveSimObject(simulatorObject.rootObject);
			}

			//returns -1 if just updating
			return -1;
		}
		catch(SQLException ex)
		{
			//returns -2 for sql-exception
			ex.printStackTrace();
			return -2;
		}
		
	}

	/*@Override
	public boolean updateObject(int id,SimulatorObject object) {
		
		Connection connection = Settings.getDBC();
		
		try
		{
			String query = "UPDATE Objects SET Name=?, Effect=?, Voltage=?, Current=?, Impact_Degree_ID=? WHERE ID=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, object.getName());
			statement.setDouble(2, object.getEffect());
			statement.setDouble(3, object.getVoltage());
			statement.setDouble(4, object.getCurrent());
			//use impact id in the simulator-object-class instead
			statement.setInt(5, object.get)
			statement.setInt(6, id);
			
			List<SimulatorObject> sons = object.getSons();
			
			for(int i = 0; i<sons.size(); i++)
			{
				if(updateObject(id, object));
			}
			
			statement.executeUpdate();
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
		return false;
	//TODO
		
	}*/

	//only delete objects that hasnt been used in a simulation!
	//will throw SQLException if it has been used in a simulation!
	@Override
	public boolean deleteObject(int object_id) {
	
		try
		{
			Connection connection = Settings.getDBC();
			
			String query = "DELETE FROM Part_Objects WHERE Father_ID=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, object_id);
			statement.executeUpdate();
			
			String nextQuery = "DELETE FROM Part_Objects WHERE Son_ID=?";
			PreparedStatement nextStatement = connection.prepareStatement(nextQuery);
			nextStatement.setInt(1, object_id);
			nextStatement.executeUpdate();
			
			String anotherQuery = "DELETE FROM Objects WHERE ID=?";
			PreparedStatement anotherStatement = connection.prepareStatement(anotherQuery);
			anotherStatement.setInt(2, object_id);
			anotherStatement.executeUpdate();
			
			return true;
		}catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public SimulationTicket requestSimulation(SimulationRequest request) {
		
		try
		{
			Connection connection = Settings.getDBC();
			
			String query = "INSERT INTO Simulation_Descriptions(Object_ID, timeIntervall,minimumTime,maximumTime) VALUES(?,?,?,?) RETURNING ID";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, request.getSimulation_object_id());
			statement.setLong(2, request.getIntervall());
			statement.setLong(3,request.getFrom());
			statement.setLong(4, request.getTo());
			ResultSet set = statement.executeQuery();
			// TODO: Feil på linjen over sql exception....
			
			int description_id = 0;
			
			if(set.next())
			{
				description_id = set.getInt(1);
			}
			else
			{
				description_id = Integer.MAX_VALUE;
			}
			
			String anotherQuery = "INSERT INTO Simulator_Queue_Objects(Simulator_ID,Status_ID,Simulation_Descriptions_ID) VALUES(11,?,?) RETURNING ID";
			PreparedStatement anotherStatement = connection.prepareStatement(anotherQuery);
			anotherStatement.setInt(1, 2);
			anotherStatement.setInt(2, description_id);
			ResultSet anotherSet = anotherStatement.executeQuery();
			
			int queue_id = 0;
			
			if(anotherSet.next())
			{
				queue_id = anotherSet.getInt(1);
			}
			else
			{
				queue_id  = Integer.MAX_VALUE;
			}
			
			return new SimulationTicket(queue_id,true,description_id);
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return null;
		}
		
	}

	@Override
	public boolean deleteSimulations(int id) {
		
		try
		{
			Connection connection = Settings.getDBC();
			
			String query = "DELETE FROM Simulations WHERE Sim_Description_ID=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, id);
			statement.executeUpdate();
			
			String anotherQuery = "DELETE FROM Simulation_Descriptions WHERE ID=?";
			PreparedStatement anotherStatement = connection.prepareStatement(anotherQuery);
			anotherStatement.setInt(1, id);
			anotherStatement.executeUpdate();
			
			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean changeAccessLevel(String username, String access_level) {
		
		try
		{
			Connection connection = Settings.getDBC();
			
			int access_level_id = AccessLevel.getInstance().getID(access_level);
			
			String query = "UPDATE Users set Access_Level_ID=? WHERE Username=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, access_level_id);
			statement.setString(2, username);
			
			return true;
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			return false;
		}
		catch(AccessLevelIDNotFoundException ex)
		{
			ex.printStackTrace();
			return true;
		}
	}

	@Override
	public int getSimulationStatus(SimulationTicket ticket) {
		
		try
		{
			Connection connection = Settings.getDBC();
			
			int queueID = ticket.getQueueID();
			System.out.println("Ticket queue id: "+ticket.getQueueID());
			System.out.println("Ticket description id: "+ticket.getDescriptionID());
			String query = "SELECT Status_ID FROM Simulator_Queue_Objects WHERE ID=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, queueID);
			ResultSet set = statement.executeQuery();
			
			if(set.next())
			{
				return set.getInt(1);
			}
			else
			{
				return Integer.MAX_VALUE;
			}
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
			
			return Integer.MAX_VALUE;
		}
	}

	@Override
	public boolean setUp(String hostname, String port, String name,
			String user, String password) {
	
		return Settings.writeConfig(hostname, port,  name, user, password);
		
	}

	@Override
	public boolean settingsLoadable() {

		return Settings.loadable();
	}

	@Override
	public SimObjectTree loadObject(int id) {
		
		
		try 
		{
			SimObjectTree tree = new SimObjectTree();
			tree.rootObject = getSimObject(id);
			
			tree.rootObject.addChildren(getChildObjects(id));
			
			
			return tree;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private ArrayList<SimObject> getChildObjects(int id) throws SQLException
	{
		ArrayList<SimObject> children = new ArrayList<SimObject>();
		
		ArrayList<Integer> childrenIDs = getChildren(id);
		
		for(int i = 0; i<childrenIDs.size(); i++)
		{
			SimObject child = getSimObject(childrenIDs.get(i));
			// TODO: feil her en plass (feil i rekursiviteten)
			//ArrayList<SimObject> grandChildren = getChildObjects(childrenIDs.get(i));
			
			//child.addChildren(grandChildren);
			
			children.add(child);
		}
		
		return children;
	}
	
	/*private SimObject getSimObject(int id) throws SQLException
	{		
		Connection connection = Settings.getDBC();
		
		String query = "SELECT id, name, effect, voltage, current, usage_pattern_id,latitude, longitude from objects where id=?";
		
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, id);
		
		ResultSet set = preparedStatement.executeQuery();
		
		if(set.next())
		{
			SimObject simObject = new SimObject(id);
			simObject.name = set.getString(2);
			simObject.effect = set.getFloat(3);
			simObject.volt = set.getFloat(4);
			//simObjectTree.rootObject.current = set.getFloat(5);
			simObject.usagePattern = set.getInt(6);
			simObject.latitude = set.getInt(7);
			simObject.longitude = set.getInt(8);
			
			return simObject;
		}
		//if it doesnt exist in the database
		else
		{
			return null;
		}
	}*/
	
	private ArrayList<Integer> getChildren(int object_id) throws SQLException
	{
		Connection connection = Settings.getDBC();
		
		ArrayList<Integer> sonIDs = new ArrayList<Integer>();
		
		String query = "select son_id from part_objects where father_id = ?";
		PreparedStatement preparedStatement = connection.prepareStatement(query);
		preparedStatement.setInt(1, object_id);
		ResultSet set = preparedStatement.executeQuery();
		while(set.next())
		{
			sonIDs.add(object_id);
		}
		
		return sonIDs;
	}

	@Override
	public ArrayList<SimObject> getSimObjects() {
		
		ArrayList<SimObject> simObjects = new ArrayList<SimObject>();
		
		try
		{
			Connection connection = Settings.getDBC();
			String query = "SELECT id, name, effect, voltage, current, usage_pattern_id,latitude, longitude from objects";
			PreparedStatement preparedStatement = connection.prepareStatement(query);
			ResultSet set = preparedStatement.executeQuery();

		
			while(set.next())
			{
				SimObject simObject = new SimObject(set.getInt(1));
				simObject.name = set.getString(2);
				simObject.effect = set.getFloat(3);
				simObject.volt = set.getFloat(4);
				//simObjectTree.rootObject.current = set.getFloat(5);
				simObject.usagePattern = set.getInt(6);
				simObject.latitude = set.getInt(7);
				simObject.longitude = set.getInt(8);
				simObjects.add(simObject);
				
			}
			
			for(int i = 0; i<simObjects.size(); i++)
			{
				SimObject simObject = simObjects.get(i);
				simObject.addChildren(this.getChildObjects(simObject.getID()));
			}
			
			
		}
		catch(SQLException ex)
		{
			ex.printStackTrace();
		}
		
		return simObjects;
	}

	@Override
	public int saveObject(HikstObject simObject) {
		
		
		try {
			if(existsInDatabase(simObject))
			{
				String query = "UPDATE Objects set Name=?, Effect=?, Voltage=?, Current=?," +
						" Usage_Pattern=?, Latitude=?, Longitude=?, Self_Temperature=?" +
						", Target_Temperature=?, Base_Area=?, Base_Heat=?,Heat_Loss=? where ID=?";
				PreparedStatement preparedStatement = Settings.getDBC().prepareStatement(query);
				preparedStatement.setString(1,simObject.name);
				preparedStatement.setDouble(2, simObject.effect);
				preparedStatement.setDouble(3, simObject.voltage);
				preparedStatement.setDouble(4, simObject.current);
				preparedStatement.setInt(5, simObject.usage_pattern_ID);
				preparedStatement.setDouble(6, simObject.latitude);
				preparedStatement.setDouble(7, simObject.longitude);
				preparedStatement.setDouble(8, simObject.self_temperature);
				preparedStatement.setDouble(9, simObject.target_temperature);
				preparedStatement.setDouble(9, simObject.base_area);
				preparedStatement.setDouble(10, simObject.base_height);
				preparedStatement.setDouble(11, simObject.heat_loss_rate);
				preparedStatement.setInt(12, simObject.getID());
				preparedStatement.executeUpdate();
				
				//returning the existing object-id
				return simObject.getID();
			}
			else
			{
				String query = "INSERT INTO Objects VALUES(?,?,?,?,?,?,?,?,?,?,?,?) Returning *";
				PreparedStatement  preparedStatement = Settings.getDBC().prepareStatement(query);
				preparedStatement.setString(1,simObject.name);
		
				preparedStatement.setDouble(2, (simObject.effect.isNaN()? null : simObject.effect) );
				preparedStatement.setDouble(3, (simObject.voltage.isNaN()? null : simObject.voltage) );
				preparedStatement.setDouble(4, (simObject.current.isNaN()? null : simObject.current) );
				preparedStatement.setInt(5, simObject.usage_pattern_ID);
				preparedStatement.setDouble(6, (simObject.latitude.isNaN()? null : simObject.latitude) );
				preparedStatement.setDouble(7, (simObject.longitude.isNaN()? null : simObject.longitude) );
				preparedStatement.setDouble(8, (simObject.self_temperature.isNaN()? null : simObject.self_temperature) );
				preparedStatement.setDouble(9, (simObject.target_temperature.isNaN()? null : simObject.target_temperature) );
				preparedStatement.setDouble(9, (simObject.base_area.isNaN()? null : simObject.base_area) );
				preparedStatement.setDouble(10, (simObject.base_height.isNaN()? null : simObject.base_height) );
				preparedStatement.setDouble(11, (simObject.heat_loss_rate.isNaN()? null : simObject.heat_loss_rate) );
				ResultSet set = preparedStatement.executeQuery();
				
				if(set.next()){
					
					//returning the new id of the object
					return set.getInt(1);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}	
		//returning -1 to indicate that the server couldnt save the object,
		//something must then be wrong with the code
		return -1;
	}
	
	private boolean existsInDatabase(HikstObject simObject)
	{
		return false;
	}
}
